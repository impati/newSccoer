package com.example.newscoccer.SearchService.record.team;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.team.info.champions.TeamChampionsInfoRequest;
import com.example.newscoccer.SearchService.team.info.champions.TeamChampionsInfoResponse;
import com.example.newscoccer.SearchService.team.info.league.TeamLeagueInfoRequest;
import com.example.newscoccer.SearchService.team.info.league.TeamLeagueInfoResponse;
import com.example.newscoccer.domain.Direction;
import com.example.newscoccer.domain.SortType;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultSearchTeamRecord implements SearchTeamRecord{
    private final EntityRecordInfo<TeamLeagueInfoRequest, TeamLeagueInfoResponse> leagueRecordInfo;
    private final EntityRecordInfo<TeamChampionsInfoRequest , TeamChampionsInfoResponse> championsRecordInfo;
    private final TeamRepository teamRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;

    /**
     * 리그 + 시즌 으로 팀 순위를 조회
     * leagueRecordInfo 에 시즌 단위의 팀 기록을 조회해오는 기능을 가져옴
     * @return
     */
    @Override
    public SearchTeamRecordResponse searchLeagueTeamRecord(Long leagueId, int season) {
        SearchTeamRecordResponse resp = new SearchTeamRecordResponse();
        teamRepository.findByLeague(leagueId)
                        .stream().forEach(t->{
                    TeamLeagueInfoResponse ret = leagueRecordInfo.recordInfo(new TeamLeagueInfoRequest(t.getId(), season));
                    resp.getResultList()
                            .add(new TeamRecordDto(ret.getRank(),t.getName(),ret.getGameNumber(),
                                    ret.getWin(),ret.getDraw(),ret.getLose(),
                                    ret.getPoint(),ret.getGain(),ret.getLost(),ret.getDiff()));
                });

        sorting(resp.getResultList(),SortType.RANK,Direction.ASC);
        return resp;
    }

    /**
     * 시즌에 참가한 팀의 기록을 가져옴 ..
     */
    @Override
    public SearchTeamRecordResponse searchChampionsTeamRecord(int season) {
        SearchTeamRecordResponse resp = new SearchTeamRecordResponse();

        teamChampionsRecordRepository.findTeamBySeason(season)
                .stream()
                .forEach(t->{
                    TeamChampionsInfoResponse ret = championsRecordInfo.recordInfo(new TeamChampionsInfoRequest(t.getId(), season));
                    resp.getResultList().add(new TeamRecordDto(ret.getRank(),t.getName(),ret.getWin(),ret.getDraw(),ret.getLose(),ret.getGain(),ret.getLost()));
                });

        sorting(resp.getResultList(),SortType.RANK,Direction.ASC);
        return resp;
    }


    private void sorting(List<TeamRecordDto> resultList , SortType sortType, Direction direction){
        if(sortType == SortType.RANK){
            resultList.sort((e1,e2)->{
                if(e1.getRank() > e2.getRank()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getRank() == e2.getRank()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }

    }
}
