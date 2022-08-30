package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.domain.record.TeamRecord;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * 두 팀간 맞대결 정보
 * 리그
 *      1.리그 전적 비교
 *      2.최근 리그 양팀 맞대결
 *      3.탑플레이어
 *
 * 챔피언스리그
 *      1.챔피언스 전적 비교 (전체 , 현재)
 *      2.최근 챔피언스 양팀 맞대결
 *      3.탑플레이어
 *
 * 공통
 * 양팀 총 상대 전적.
 *
 *
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultFaceToHead implements FaceToHead{


    private final RoundRepository roundRepository;

    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;


    /**
     * 팀A  , 팀B 의 리그 , 침피언스 리그 ,, 전체 전적(승,무,패) + 평균 득점 계산
     * @param roundId
     * @return
     */
    @Override
    public TotalComparisonRecordResponse totalComparison(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);

        RoundFeature <List<TeamRecord>> feature = new RoundFeature<>() {
            @Override
            public List<TeamRecord> leagueSolved() {
                return teamLeagueRecordRepository.findByRound(round).stream().collect(toList());
            }

            @Override
            public List<TeamRecord> championsSolved() {
                return teamChampionsRecordRepository.findByRound(round).stream().collect(toList());
            }
        };

        List<TeamRecord> ret = new RoundTemplate().action(round,feature);

        Team teamA = ret.get(0).getTeam();
        Team teamB = ret.get(1).getTeam();

        TotalComparisonRecordResponse resp = new TotalComparisonRecordResponse(teamA.getName(),teamB.getName());

        leagueAllComparison(round, teamA, teamB, resp);
        championsAllComparison(round, teamA, teamB, resp);
        return resp;
    }

    private void championsAllComparison(Round round, Team teamA, Team teamB, TotalComparisonRecordResponse resp) {
        List<Round > roundList = teamChampionsRecordRepository.findRoundListByTeam(teamB, round.getCreateDate());
        teamChampionsRecordRepository.findAllByRoundListAndTeam(roundList, teamA)
                .stream()
                .forEach(ele-> resp.update(ele.getMatchResult(),ele.getScore(),ele.getOppositeScore()));
    }

    private void leagueAllComparison(Round round, Team teamA, Team teamB, TotalComparisonRecordResponse resp) {
        List<Round> roundList = teamLeagueRecordRepository.findRoundListByTeam(teamB, round.getCreateDate());
        teamLeagueRecordRepository.findAllByRoundListAndTeam(roundList, teamA)
                .stream()
                .forEach(ele-> resp.update(ele.getMatchResult(),ele.getScore(),ele.getOppositeScore()));
    }


    /**
     *  시즌 +  라운드 이전에 선수들의 골 +  어시 , 레이팅 순으로 5명만 조회
     * @param roundId
     * @return
     */
    @Override
    public TopPlayerResponse top5Player(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);

        RoundFeature<TopPlayerResponse> feature = new RoundFeature<>() {
            @Override
            public TopPlayerResponse leagueSolved() {
                List<TeamLeagueRecord> tlrList  = teamLeagueRecordRepository.findByRound(round);

                Team teamA = tlrList.get(0).getTeam();
                Team teamB = tlrList.get(1).getTeam();

                TopPlayerResponse resp = new TopPlayerResponse();

                resp.setTeamAPlayerList(playerLeagueRecordRepository.findTop5Player(teamA,round.getSeason(), round.getRoundSt(),PageRequest.of(0,5)));
                resp.setTeamBPlayerList(playerLeagueRecordRepository.findTop5Player(teamB,round.getSeason(), round.getRoundSt(),PageRequest.of(0,5)));



                return resp;
            }

            @Override
            public TopPlayerResponse championsSolved() {
                ChampionsRound championsRound = (ChampionsRound) round;
                List<TeamChampionsRecord> tcrList = teamChampionsRecordRepository.findByRound(championsRound);
                Team teamA = tcrList.get(0).getTeam();
                Team teamB = tcrList.get(1).getTeam();

                int firstAndSecond = tcrList.get(0).getFirstOrSecond();

                TopPlayerResponse resp = new TopPlayerResponse();
                championsTopPlayer(teamA,round,firstAndSecond,resp.getTeamAPlayerList());
                championsTopPlayer(teamB,round,firstAndSecond,resp.getTeamBPlayerList());
                resp.setTeamAPlayerList(sortingAndCut(resp.getTeamAPlayerList()));
                resp.setTeamBPlayerList(sortingAndCut(resp.getTeamBPlayerList()));

                return resp;
            }
        };

        return new RoundTemplate().action(round,feature);

    }
    // 시즌 챔피언스 기록 중 시즌 , 현재 라운드보다 이전의 기록 중 탑 플레이어들을 가져옴
    private void championsTopPlayer(Team team,Round round , int firstAndSecond,List<TopPlayerDto> result){
        Map<Long,TopPlayerDto> map = new HashMap<> ();

        playerChampionsRecordRepository.findByTeamAndSeason(team,round.getSeason())
                .stream()
                .filter(pcr->!((pcr.getRound().getRoundSt() == round.getRoundSt())&& (pcr.getFirstOrSecond() >= firstAndSecond)))
                .forEach(pcr->{
                    Long playerId = pcr.getPlayer().getId();
                    if(map.containsKey(playerId)){
                        map.get(playerId).update(pcr.getGoal(),pcr.getAssist());
                    }
                    else{
                        TopPlayerDto dto = new TopPlayerDto(pcr.getPlayer().getName(), (long) pcr.getGoal(), (long) pcr.getAssist(),pcr.getPlayer().getRating());
                        map.put(playerId,dto);
                    }
                });
        for (var ele : map.keySet()) {
            result.add(map.get(ele));
        }
    }
    // 탑플레이어 중 조건에 맞게 정렳하고 5개만 가져옴 .
    private List<TopPlayerDto> sortingAndCut(List<TopPlayerDto> list){
        list.sort((e1,e2)->{
            if(e1.getGoal() + e1.getAssist() > e2.getGoal() + e2.getAssist()) return -1;
            else if(e1.getGoal() + e1.getAssist() < e2.getGoal() + e2.getAssist()) return 1;
            else {
                if(e1.getRating() > e2.getRating()) return -1;
                else if(e1.getRating() == e2.getRating()) return 0;
                else return 1;
            }
        });
        return list.subList(0,5);
    }



    /**
     * 최근  5경기 맞대결을 가져옴
     */
    @Override
    public RecentRecordResponse recentRecord(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);
        RoundFeature<RecentRecordResponse> feature = new RoundFeature<>() {
            @Override
            public RecentRecordResponse leagueSolved() {
                List<TeamLeagueRecord> tlrList  = teamLeagueRecordRepository.findByRound(round);

                TeamLeagueRecord tlrA = tlrList.get(0);
                TeamLeagueRecord tlrB = tlrList.get(1);

                RecentRecordResponse resp = new RecentRecordResponse();
                List<Round> roundList = teamLeagueRecordRepository.findRoundListByTeam(tlrA.getTeam(),round.getCreateDate());
                teamLeagueRecordRepository.findByRoundListAndTeam(roundList,tlrB.getTeam(), PageRequest.of(0,5))
                        .stream()
                        .forEach(tlr->{
                            SimpleRecordResultDto dto = new SimpleRecordResultDto(tlr.getRound().getSeason(),tlr.getRound().getRoundSt());
                            dto.updateTeamA(tlrA.getTeam().getName(),tlr.getOppositeScore(), MatchResultUtils.oppositeMatchResult(tlr.getMatchResult()));
                            dto.updateTeamB(tlr.getTeam().getName(),tlr.getScore(),tlr.getMatchResult());
                            resp.getSimpleRecordResultDtoList().add(dto);
                        });
                return resp;
            }


            @Override
            public RecentRecordResponse championsSolved() {
                List<TeamChampionsRecord> tcrList  = teamChampionsRecordRepository.findByRound(round);

                TeamChampionsRecord tcrA = tcrList.get(0);
                TeamChampionsRecord tcrB = tcrList.get(1);

                RecentRecordResponse resp = new RecentRecordResponse();
                List<Round> roundList = teamChampionsRecordRepository.findRoundListByTeam(tcrA.getTeam(),round.getCreateDate());
                teamChampionsRecordRepository.findByRoundListAndTeam(roundList,tcrB.getTeam(), PageRequest.of(0,5))
                        .stream()
                        .forEach(tlr->{
                            SimpleRecordResultDto dto = new SimpleRecordResultDto(tlr.getRound().getSeason(),tlr.getRound().getRoundSt());
                            dto.updateTeamA(tcrA.getTeam().getName(),tlr.getOppositeScore(), MatchResultUtils.oppositeMatchResult(tlr.getMatchResult()));
                            dto.updateTeamB(tlr.getTeam().getName(),tlr.getScore(),tlr.getMatchResult());
                            resp.getSimpleRecordResultDtoList().add(dto);
                        });
                return resp;

            }
        };

        return new RoundTemplate().action(round,feature);
    }



    /**
     * 리그인 경우  시즌의 정보를 종합해서 리턴 (단 , 라운드 이전의 정보들)
     *
     * 챔피언스 경우  시즌 정보와 전체 시즌 정보를 종합해서 리턴
     */
    @Override
    public ComparisonRecordResponse comparison(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);
        RoundFeature<ComparisonRecordResponse> feature = new RoundFeature<>() {
            @Override
            public ComparisonRecordResponse leagueSolved() {
                ComparisonRecordResponse resp = new ComparisonRecordResponse();
                teamLeagueRecordRepository.findByRound(round)
                        .stream()
                        .forEach(tlr->{
                            Team team = tlr.getTeam();
                            ComparisonRecordDto dto = new ComparisonRecordDto(team.getName());
                            teamLeagueRecordRepository.findByTeamAndSeasonLessThanRoundSt(team.getId(),round.getSeason(),round.getRoundSt())
                                    .stream()
                                    .forEach(element->{
                                        dto.leagueUpdate(element.getMatchResult(),element.getScore(), element.getOppositeScore());
                                    });
                            resp.getRecordList().add(dto);
                        });

                return resp;
            }

            @Override
            public ComparisonRecordResponse championsSolved() {
                ChampionsRound championsRound = (ChampionsRound) round;
                ComparisonRecordResponse resp = new ComparisonRecordResponse();
                teamChampionsRecordRepository.findByRound(round)
                        .stream()
                        .forEach(tcr->{
                            Team team = tcr.getTeam();
                            ComparisonRecordDto dto = new ComparisonRecordDto(team.getName());
                            // 팀의 전체 챔피언스 기록을 가져온 후 라운드의 시즌과 같으면 시즌처리를 한번 더 호출해 줌
                            teamChampionsRecordRepository.findByTeam(team.getId())
                                    .stream()
                                    .forEach(element->{
                                        ChampionsRound atThatTime = element.getRound();
                                        if(championsRound.getSeason() > atThatTime.getSeason()){
                                            dto.championsTotalUpdate(element.getMatchResult(),element.getScore(), element.getOppositeScore());
                                        }
                                        else  if( championsRound.getSeason() == atThatTime.getSeason()) {
                                            if(championsRound.getRoundSt() < atThatTime.getRoundSt()){
                                                dto.championsTotalUpdate(element.getMatchResult(), element.getScore(), element.getOppositeScore());
                                                dto.championsSeasonUpdate(element.getMatchResult());
                                            }
                                            else if(championsRound.getRoundSt() == atThatTime.getRoundSt() && tcr.getFirstOrSecond() > element.getFirstOrSecond()){
                                                dto.championsTotalUpdate(element.getMatchResult(), element.getScore(), element.getOppositeScore());
                                                dto.championsSeasonUpdate(element.getMatchResult());
                                            }

                                        }



                                    });
                            resp.getRecordList().add(dto);
                        });
                return resp;
            }
        };

        return new RoundTemplate().action(round,feature);
    }


}
