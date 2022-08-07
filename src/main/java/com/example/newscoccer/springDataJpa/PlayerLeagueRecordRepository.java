package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.SearchService.round.common.faceToHead.TopPlayerDto;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.dto.PlayerParticipate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerLeagueRecordRepository extends JpaRepository<PlayerLeagueRecord ,Long> {


    /**
     * 선수의 시즌 리그 정보를 가져옴 .
     * @param playerId
     * @param season
     * @return
     */
    @Query(" select plr from PlayerLeagueRecord plr " +
            " join plr.player p " +
            " join plr.round r " +
            " where p.id = :player and r.season = :season " +
            " order by plr.createDate ")
    List<PlayerLeagueRecord> findByPlayerAndSeason(@Param("player") Long playerId ,@Param("season") int season);

    /**
     *  선수의 리그 전체 기록을 가져옴 .
     */
    @Query("select plr from PlayerLeagueRecord plr " +
            " join fetch plr.round r " +
            " join  plr.player p " +
            " where p.id = :player ")
    List<PlayerLeagueRecord> findPlayerLeagueAll(@Param("player")Long playerId);


    /**
     * 선수정보 , 선수가 해당 시즌에 참가한 경기수
     * @param team
     * @param season
     * @return
     */
    @Query(" select new com.example.newscoccer.springDataJpa.dto.PlayerParticipate(p, count(plr.id)) from PlayerLeagueRecord  plr " +
            " join plr.round r " +
            " join plr.team t " +
            " join plr.player p " +
            " where r.season = :season and t.id = :team " +
            " group by p.id ")
    List<PlayerParticipate> findPlayerParticipate(@Param("team") Long team , @Param("season") int season);


    /**
     * 팀 + 라운드로 해당 라운드에 참가한 선수들을 가져옴 .
     */
    @Query("select plr from PlayerLeagueRecord plr " +
            " join fetch plr.team t " +
            " join fetch plr.round r " +
            " where t = :team and r = :round")
    List<PlayerLeagueRecord> findByTeamAndRound(@Param("team") Team team , @Param("round")Round round);


    @Query("select new com.example.newscoccer.SearchService.round.common.faceToHead.TopPlayerDto(p.name,sum(plr.goal),sum(plr.assist),p.rating) " +
            " from PlayerLeagueRecord  plr " +
            " join plr.player p " +
            " join plr.round r" +
            " join plr.team t " +
            " where t = :team and r.season = :season and r.roundSt < :roundSt " +
            " group by p.id " +
            " order by sum(plr.goal) + sum(plr.assist) desc ,  p.rating desc ")
    List<TopPlayerDto> findByTeam(@Param("team") Team team , @Param("season") int season , @Param("roundSt") int roundSt, Pageable pageable );





}
