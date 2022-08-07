package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

                resp.setTeamAPlayerList(playerLeagueRecordRepository.findByTeam(teamA,round.getSeason(), round.getRoundSt(),PageRequest.of(0,5)));
                resp.setTeamBPlayerList(playerLeagueRecordRepository.findByTeam(teamB,round.getSeason(), round.getRoundSt(),PageRequest.of(0,5)));



                return resp;
            }

            @Override
            public TopPlayerResponse championsSolved() {
                return null;
            }
        };

        return new RoundTemplate().action(round,feature);

    }

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
                            SimpleRecordResultDto dto = new SimpleRecordResultDto();
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
                            SimpleRecordResultDto dto = new SimpleRecordResultDto();
                            dto.updateTeamA(tcrA.getTeam().getName(),tlr.getOppositeScore(), MatchResultUtils.oppositeMatchResult(tlr.getMatchResult()));
                            dto.updateTeamB(tlr.getTeam().getName(),tlr.getScore(),tlr.getMatchResult());
                            resp.getSimpleRecordResultDtoList().add(dto);
                        });
                return resp;

            }
        };

        return new RoundTemplate().action(round,feature);
    }






    @Override
    public TotalComparisonRecordResponse totalComparison(Long roundId) {
        return null;
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