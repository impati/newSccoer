package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.record.*;
import com.example.newscoccer.springDataJpa.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * GameRecordDto 를 받아서 경기결과를 저장.
 * 역시나 반 복문제  분기는 싫다!
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultGameRecordRegister implements GameRecordRegister{
    private final RoundRepository roundRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;

    /**
     * 1. 라운드를 통해 팀A vs 팀B 를 조회해옴
     * 2. teamId 가 작은 팀이  팀 A 임
     * 3. 전달 받은 데이터로 스코어 먼저 세팅해줌.
     * 4. 승부 결과를 결정
     * 5. 선수들 데이터 세팅 -> 합산 혹은 평균을 팀 데이터에 전달.
     * 6. 팀 데이터 세팅
     * 7. 레이팅 세팅
     * 8. PAIR Status 로 설정.
     *
     */
    @Override
    public void gameRecordRegister(GameRecordDto dto) {

        Round round = roundRepository.findById(dto.getRoundId()).orElse(null);

        RoundFeature<Void> roundFeature = new RoundFeature<>() {
            @Override
            public Void leagueSolved() {
                List<TeamLeagueRecord> tlrList = teamLeagueRecordRepository.findByRound(round);
                TeamLeagueRecord tlrA = tlrList.get(0); // teamId 가 더 작은 팀.
                TeamLeagueRecord tlrB = tlrList.get(1); // teamId 가 더 큰 팀.

                // 스코어 먼저 세팅.
                scoreFirst(dto,tlrA,tlrB);

                // 처리
                treat(tlrA,tlrB,playerLeagueRecordRepository.findByTeamAndRound(tlrA.getTeam(),round).stream().collect(toList()), dto);
                treat(tlrB,tlrA,playerLeagueRecordRepository.findByTeamAndRound(tlrB.getTeam(),round).stream().collect(toList()), dto);

                round.setRoundStatus(RoundStatus.PAIR);
                return null;
            }

            @Override
            public Void championsSolved() {
                List<TeamChampionsRecord > tcrList = teamChampionsRecordRepository.findByRound(round);
                TeamChampionsRecord tcrA = tcrList.get(0);
                TeamChampionsRecord tcrB = tcrList.get(1);

                scoreFirst(dto,tcrA,tcrB);

                // 처리

                treat(tcrA,tcrB,playerChampionsRecordRepository.findByTeamAndRound(tcrA.getTeam(),round).stream().collect(toList()), dto);
                treat(tcrB,tcrA,playerChampionsRecordRepository.findByTeamAndRound(tcrB.getTeam(),round).stream().collect(toList()), dto);

                round.setRoundStatus(RoundStatus.PAIR);



                return null;
            }
        };

        new RoundTemplate().action(round,roundFeature);

    }


    @Data
    static class RecordSummationDto{
        private int goal;
        private int assist;
        private int pass;
        //슈팅
        private int shooting;
        // 유효 슈팅
        private int validShooting;
        //파울
        private int foul;
        //선방
        private int goodDefense;
        private int grade;
        private int bestGrade;

        public void sum(int goal, int assist,int pass,
                           int shooting ,int validShooting,int foul,
                           int defense , int grade) {
            this.goal += goal;
            this.assist += assist;
            this.pass += pass;
            this.shooting += shooting;
            this.validShooting += validShooting;
            this.foul += foul;
            this.goodDefense += defense;
            this.grade += grade;
        }
    }



    private void scoreFirst(GameRecordDto dto , TeamRecord tlrA ,TeamRecord tlrB) {
        tlrA.setScore(dto.getTeams().get(0).getScore());
        tlrB.setScore(dto.getTeams().get(1).getScore());
    }
    private void treat(TeamRecord myTeam ,TeamRecord oppositeTeam, List<PlayerRecord> plrList , GameRecordDto dto){

        // 경기 승부 결과
        MatchResult matchResult = myTeamMatchResult(myTeam,oppositeTeam);
        // 현재 팀 선수들의 정보를 모와옴
        RecordSummationDto resp = playerTreat(matchResult,dto,plrList);
        // 최고의 선수
        plrList.stream().filter(p->p.getGrade() == resp.getBestGrade()).forEach(ele->ele.setBest(true));
        // 팀 세팅
        teamSolved(myTeam,dto,resp);
    }
    private RecordSummationDto playerTreat(MatchResult matchResult, GameRecordDto dto,List<PlayerRecord> plrList){
        RecordSummationDto resp = new RecordSummationDto();
        int value = 0;
        for (int i = 0; i<  plrList.size();i++ ) {
            PlayerRecord playerRecord = plrList.get(i);
            GameResultPlayerDto playerDto = dto.getPlayers()
                    .stream()
                    .filter(p->p.getPlayerId().equals(playerRecord.getPlayer().getId())).findFirst().orElse(null);
            if(value < playerDto.getGrade()){
                value = playerDto.getGrade();
            }
            playerRecord.update(playerDto.getGoal(),playerDto.getAssist(),playerDto.getPass(),
                    playerDto.getShooting(),playerDto.getValidShooting(),playerDto.getFoul(),playerDto.getDefense(),playerDto.getGrade(),matchResult);

            //팀에게 전달한 총합 데이터들을 수집 중
            resp.sum(playerDto.getGoal(),playerDto.getAssist(),playerDto.getPass(),
                    playerDto.getShooting(),playerDto.getValidShooting(),playerDto.getFoul(),playerDto.getDefense(),playerDto.getGrade());

        }
        // 별도 처리 bestGrade
        resp.setBestGrade(value);
        // 별도 처리 평점 평균
        resp.setGrade(resp.getGrade()/plrList.size());
        return resp;

    }
    private MatchResult myTeamMatchResult(TeamRecord trA, TeamRecord trB){
        if(trA.getScore() > trB.getScore()) trA.setMatchResult(MatchResult.WIN);
        else if(trA.getScore() == trB.getScore()) trA.setMatchResult(MatchResult.DRAW);
        else trA.setMatchResult(MatchResult.LOSE);
        return trA.getMatchResult();
    }
    private void teamSolved(TeamRecord tlr  , GameRecordDto dto,RecordSummationDto summationDto){
        GameResultTeamDto teamDto = dto.getTeams().stream().filter(t -> t.getTeamId().equals(tlr.getTeam().getId())).findFirst().orElse(null); // tlr의 데이터
        GameResultTeamDto oppoSite = dto.getTeams().stream().filter(t->!t.equals(teamDto)).findFirst().orElse(null); // 상대 데이터

        tlr.teamUpdate(teamDto.getScore(),oppoSite.getScore(),teamDto.getShare(),teamDto.getCornerKick(), teamDto.getFreeKick());// 팀정보 세팅
        tlr.playerRecordSummation(summationDto.getShooting(),summationDto.getValidShooting(),summationDto.getFoul(),summationDto.getGoodDefense(), // 선수들 정보의 합 세팅
                summationDto.getPass(),summationDto.getGrade());
    }


}
