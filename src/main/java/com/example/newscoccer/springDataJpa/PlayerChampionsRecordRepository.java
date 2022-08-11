package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.springDataJpa.dto.PlayerParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlayerChampionsRecordRepository extends JpaRepository<PlayerChampionsRecord,Long> , PlayerChampionsRepositoryQuerydsl {


    /**
     * 시즌 + 선수 정보로 챔피언스 기록을 가져옴. 16 강 부터 차례로
     * @param playerId
     * @param season
     * @return
     */
    @Query(" select pcr from PlayerChampionsRecord pcr " +
            " join pcr.player p " +
            " join pcr.round r " +
            " where p.id = :player and r.season = :season " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE" +
            " order by r.roundSt desc")
    List<PlayerChampionsRecord> findByPlayerAndSeason(@Param("player") Long playerId , @Param("season")int season);


    /**
     * 선수의 모든 챔피언스 기록을 조회
     * @param playerId
     * @return
     */
    @Query(" select pcr from PlayerChampionsRecord pcr " +
            " join fetch pcr.round " +
            " join pcr.player p " +
            " where p.id = :player ")
    List<PlayerChampionsRecord> findPlayerChampionsAll(@Param("player") Long playerId);


    /**
     * 선수정보 , 선수가 해당 시즌에 챔피언스 리그에 참가한 경기수
     * @param team
     * @param season
     * @return
     */
    @Query(" select new com.example.newscoccer.springDataJpa.dto.PlayerParticipate(p, count(pcr.id)) from PlayerChampionsRecord  pcr " +
            " join pcr.round r " +
            " join pcr.team t " +
            " join pcr.player p " +
            " where r.season = :season and t.id = :team " +
            " group by p.id ")
    List<PlayerParticipate> findPlayerParticipate(@Param("team") Long team , @Param("season") int season);


    /**
     *
     * 시즌에 팀에 있던 참가한 선수들 모두 .
     * @param teamList
     * @param season
     * @return
     */
    @Query(" select distinct p from PlayerChampionsRecord pcr " +
            " join pcr.player p " +
            " join fetch p.team t " +
            " join pcr.round r " +
            " where t in(:teamList) and r.season = :season " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE ")
    List<Player> findPlayerByTeamListAndSeason(@Param("teamList") List<Team> teamList , @Param("season") int season);

    /**
     * 선수정보 , 선수가 해당 시즌에 챔피언스 리그 정보
     * @param team
     * @param season
     * @return
     */
    @Query(" select pcr from PlayerChampionsRecord  pcr " +
            " join fetch pcr.round r " +
            " join pcr.team t " +
            " join fetch pcr.player p " +
            " where r.season = :season and t = :team ")
    List<PlayerChampionsRecord> findByTeamAndSeason(@Param("team") Team team , @Param("season") int season);


    /**
     * 팀 + 라운드로 해당 라운드에 참가한 선수들을 가져옴 .
     */
    @Query("select pcr from PlayerChampionsRecord pcr " +
            " join pcr.team t " +
            " join pcr.round r " +
            " where t = :team and r = :round")
    List<PlayerChampionsRecord> findByTeamAndRound(@Param("team") Team team , @Param("round") Round round);




    /**
     * 시즌 , 챔피언스 라운드에 진출한 선수 기록을 가져옴.
     */
    @Query("select pcr from PlayerChampionsRecord  pcr " +
            " join pcr.team t " +
            " join pcr.round r " +
            "where r.season = :season and r.roundSt = :roundSt")
    List<PlayerChampionsRecord> findBySeasonAndRoundSt(@Param("season") int season , @Param("roundSt") int roundSt);







}
