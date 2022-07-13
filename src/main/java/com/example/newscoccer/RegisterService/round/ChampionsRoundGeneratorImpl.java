package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.domain.record.TeamRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.springDataJpa.dto.TeamScoreDto;
import com.example.newscoccer.support.RandomNumber;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 *  내일 주석 +  테스트
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChampionsRoundGeneratorImpl implements ChampionsRoundGenerator{

    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final RoundRepository roundRepository;
    private static final int SixTeenRoundSt = 16;
    private static final int EightRoundSt = 8;
    private static final int semifinals = 4;
    private static final int finals = 2;
    private static final int FirstSeason = 0;

    /**
     *  인덱스 , 팀A vs 팀 B
     */
    @Setter
    @Getter
    static class TeamPair{
        private int index;
        private Team teamA = null;
        private Team teamB = null;

        public TeamPair(int index) {
            this.index = index;
        }
        public void addTeam(Team team){
            if(teamA == null) teamA = team;
            else teamB = team;
        }
    }


    /**
     *  roundSt 도메인 : 16,8,4 ,2
     *  season , roundSt 정보의 팀 챔피언스 레코드를 생성.
     */
    @Override
    public void generator(int season, int roundSt) {
        if(roundRepository.findChampionsRound(season,roundSt).stream().count() >0)return ;
        TeamPair[] teamPairs = ParticipateTeam(season, roundSt); // 참가할 팀과 인덱스 정보 , 배열의 인덱스가 실제 인엑스임.
        if(roundSt == finals){
            // 하나만 생성.
            ChampionsRound round = new ChampionsRound(season,roundSt,1);
            roundRepository.save(round);
            saveEntity(round ,teamPairs[1].getTeamA(),1);
            saveEntity(round , teamPairs[1].getTeamB(),1);
        }
        else{
            // 두개씩 생성.
            int start = roundSt / 2 , end =  roundSt - 1;
            for(int i = start ;i <= end ; i++){
                for(int k = 1 ;k <= 2 ;k++) {
                    ChampionsRound round = new ChampionsRound(season, roundSt, i);
                    roundRepository.save(round);
                    saveEntity(round, teamPairs[i].getTeamA(),k);
                    saveEntity(round, teamPairs[i].getTeamB(),k);
                }
            }
        }
    }
    // teamChampionsRecordRepository 를 저장.
    private void saveEntity(Round round , Team team,int firstAndSecond){
        TeamChampionsRecord teamChampionsRecord = TeamChampionsRecord.create(round,team,firstAndSecond);
        teamChampionsRecordRepository.save(teamChampionsRecord);
    }

    /**
     *  // 참가할 팀과 인덱스 정보 , 배열의 인덱스가 실제 인엑스임.
     */
    private TeamPair[] ParticipateTeam(int season,int roundSt) {
        TeamPair[] teamList = null;
       if(roundSt == SixTeenRoundSt) {
           if (season == FirstSeason)
               teamList = firstTeam();
           else
               teamList = preSeasonResult(season);
       }
       else
           teamList = nextRound(season,roundSt);

       return teamList;
    }

    /**
     * 이전 라운드 결과를 바탕으로 다음 라운드에 진출할 팀을 만들어서 넘겨준다.
     */
    private TeamPair[] nextRound(int season , int roundSt){
        int preRoundSt = roundSt * 2;
        TeamPair pair[] = new TeamPair[16];
        for(int i = 0 ;i <16;i++){
            pair[i] = new TeamPair(i);
        }
        int start = preRoundSt / 2 , end =  preRoundSt - 1;
        for(int i = start ; i <=end ; i++){
            List<TeamScoreDto> teamScore = teamChampionsRecordRepository.findTeamScore(season, preRoundSt, i);
            Team winner = teamScore.get(0).getScore() > teamScore.get(1).getScore() ? teamScore.get(0).getTeam() : teamScore.get(1).getTeam();
            pair[i/2].addTeam(winner);
        }
        return pair;
    }


    /**
     * 시즌이 시작될떄 챔피언스 리그 16강을 생성 .
     * 1시즌 부터
     */
    private TeamPair[] preSeasonResult(int season){
        List<Team> ret = new ArrayList<>();
        int prevSeason = season - 1;
        List<League> leagueList = leagueRepository.findAll();
        for (League league : leagueList) {
            List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository.findBySeasonTopN(league, prevSeason, PageRequest.of(0, 4));
            for (TeamLeagueRecord teamLeagueRecord : teamLeagueRecordList) {
                ret.add(teamLeagueRecord.getTeam());
            }
        }
        return assign(ret,8,15);
    }
    /**
     *  내가 선정한 TOP 16 , 처음 한번만 호출
     */
    public TeamPair[] firstTeam(){
        List<Team> ret = new ArrayList<>();
        ret.add(teamRepository.findByName("바이에른 뮌헨").orElse(null));
        ret.add(teamRepository.findByName("도르트문트").orElse(null));
        ret.add(teamRepository.findByName("프랑크부르크").orElse(null));
        ret.add(teamRepository.findByName("라이프치히").orElse(null));

        ret.add(teamRepository.findByName("파리 생제르맹").orElse(null));
        ret.add(teamRepository.findByName("유벤투스").orElse(null));
        ret.add(teamRepository.findByName("밀란").orElse(null));
        ret.add(teamRepository.findByName("나폴리").orElse(null));

        ret.add(teamRepository.findByName("맨체스터 시티").orElse(null));
        ret.add(teamRepository.findByName("첼시").orElse(null));
        ret.add(teamRepository.findByName("맨체스터 유나이티드").orElse(null));
        ret.add(teamRepository.findByName("리버풀").orElse(null));

        ret.add(teamRepository.findByName("세비야").orElse(null));
        ret.add(teamRepository.findByName("바르셀로나").orElse(null));
        ret.add(teamRepository.findByName("아틀레티코 마드리드").orElse(null));
        ret.add(teamRepository.findByName("레알 마드리드").orElse(null));
        return assign(ret,8,15);
    }

    /**
     * 16개의 팀중 랜덤 매칭.
     */
    private TeamPair[]  assign(List<Team> teams ,int start ,int end){

        int visited[] = new int [17];
        TeamPair teamPair [] = new TeamPair[17];
        for(int i = 0;i<16;i++) teamPair[i] = new TeamPair(i);

        for(int i = 0 ;i <teams.size();i++){
            while(true) {
                int rn = RandomNumber.returnRandomNumber(start, end);
                if(visited[rn] < 2){
                    visited[rn] += 1;
                    teamPair[rn].addTeam(teams.get(i));
                    break;
                }
            }
        }
        return teamPair;
    }


}
