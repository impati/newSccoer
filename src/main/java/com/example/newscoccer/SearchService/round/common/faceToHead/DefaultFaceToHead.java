package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public RecentRecordResponse recentRecord(Long roundId) {
        return null;
    }

    @Override
    public TopPlayerResponse top5Player(Long roundId) {
        return null;
    }

    @Override
    public TotalComparisonRecordResponse totalComparison(Long roundId) {
        return null;
    }


}
