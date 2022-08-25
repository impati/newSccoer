package com.example.newscoccer.SearchService.record.player;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.player.info.champoions.PlayerChampionsInfoRequest;
import com.example.newscoccer.SearchService.player.info.champoions.PlayerChampionsInfoResponse;
import com.example.newscoccer.SearchService.player.info.league.PlayerLeagueInfoRequest;
import com.example.newscoccer.SearchService.player.info.league.PlayerLeagueInfoResponse;
import com.example.newscoccer.domain.Direction;
import com.example.newscoccer.domain.SortType;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  성능 이슈 - 테스트가 너무 오래걸림.
 *  TODO : 성능 이슈 해결 .
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultSearchPlayerRecord implements SearchPlayerRecord {

    private final EntityRecordInfo<PlayerLeagueInfoRequest,PlayerLeagueInfoResponse> leagueRecordInfo;
    private final EntityRecordInfo<PlayerChampionsInfoRequest , PlayerChampionsInfoResponse> championsRecordInfo;

    private final TeamRepository teamRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;

    /**
     * 해당 시즌에 선수들 기록으 조회 ,  + sortType ,direction
     */
    @Override
    public SearchPlayerRecordResponse searchLeaguePlayerRecord(Long leagueId, int season, SortType sortType , Direction direction) {
        SearchPlayerRecordResponse resp = new SearchPlayerRecordResponse();
        List<Team> teamList = teamRepository.findByLeague(leagueId);


        playerLeagueRecordRepository.findPlayerByTeamListAndSeason(teamList,season)
                .stream()
                .forEach(p->{
                    PlayerLeagueInfoResponse dto = leagueRecordInfo.recordInfo(new PlayerLeagueInfoRequest(p.getId(), season));
                    resp.getResultList().add(new PlayerRecordDto(p.getName(),p.getTeam().getName(),
                            dto.getGoal(),dto.getAssist(),dto.getShooting(),dto.getRank(),
                            dto.getValidShooting(),dto.getFoul(),dto.getPass(), dto.getDefense()
                            ));
                });
        sorting(resp.getResultList(),sortType,direction);
        return resp;
    }


    /**
     * 해당 시즌 챔피언스리그에 진출한 선수들 기록을 조회
     * @param season
     * @param sortType
     * @param direction
     * @return
     */
    @Override
    public SearchPlayerRecordResponse searchChampionsPlayerRecord(int season, SortType sortType , Direction direction) {
        SearchPlayerRecordResponse resp = new SearchPlayerRecordResponse();
        List<Team> teamList = teamChampionsRecordRepository.findTeamBySeason(season);

        playerChampionsRecordRepository.findPlayerByTeamListAndSeason(teamList,season)
                .stream()
                .forEach(p->{
                    PlayerChampionsInfoResponse dto = championsRecordInfo.recordInfo(new PlayerChampionsInfoRequest(p.getId(),season));
                    resp.getResultList().add(new PlayerRecordDto(p.getName(),p.getTeam().getName(),
                            dto.getGoal(),dto.getAssist(),dto.getShooting(),dto.getRank(),
                            dto.getValidShooting(),dto.getFoul(),dto.getPass(), dto.getDefense()
                    ));
                });
        sorting(resp.getResultList(),sortType,direction);
        return resp;
    }


    /**
     *  조건에 맞는 정렬.
     * @param resultList
     * @param sortType
     * @param direction
     */
    private void sorting(List<PlayerRecordDto> resultList , SortType sortType, Direction direction){
        if(sortType == SortType.RANK){
            resultList.sort((e1,e2)->{
                if(e1.getRank() > e2.getRank()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getRank() == e2.getRank()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.GOAL){
            resultList.sort((e1,e2)->{
                if(e1.getGoal() > e2.getGoal()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getGoal() == e2.getGoal()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.ASSIST){
            resultList.sort((e1,e2)->{
                if(e1.getAssist() > e2.getAssist()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getAssist() == e2.getAssist()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.ATTACKPOINT){
            resultList.sort((e1,e2)->{
                if(e1.getAttackPoint() > e2.getAttackPoint()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getAttackPoint() == e2.getAttackPoint()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.SHOOTING){
            resultList.sort((e1,e2)->{
                if(e1.getShooting() > e2.getShooting()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getShooting() == e2.getShooting()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.VALIDSHOOTING){
            resultList.sort((e1,e2)->{
                if(e1.getValidShooting() > e2.getValidShooting()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getValidShooting() == e2.getValidShooting()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.FOUL){
            resultList.sort((e1,e2)->{
                if(e1.getFoul() > e2.getFoul()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getFoul() == e2.getFoul()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.PASS){
            resultList.sort((e1,e2)->{
                if(e1.getPass() > e2.getPass()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getPass() == e2.getPass()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }
        else if(sortType == SortType.DEFENSE){
            resultList.sort((e1,e2)->{
                if(e1.getDefense() > e2.getDefense()) return direction == Direction.ASC ? 1 : -1;
                else if(e1.getDefense() == e2.getDefense()) return 0;
                else return direction == Direction.ASC ? -1 : 1;
            });
        }


    }





}
