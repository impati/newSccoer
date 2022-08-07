package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round,Long> {
    /**
     * 챔피언스라운드 리턴
     */
    @Query("select cr from ChampionsRound cr where cr.season =:season and cr.roundSt =:roundSt")
    List<ChampionsRound> findChampionsRound(@Param("season") int season , @Param("roundSt") int roundSt);

    /**
     * 시즌의 리그 라운드 수
     */
    @Query("select count(r) from LeagueRound r where r.season =:season")
    Long findBySeason(@Param("season") int season);

    /**
     * * 시즌 , 라운드 , 리그로 정보를 가져옴 .
     */

    @Query("select lr from LeagueRound lr " +
            " join lr.league l " +
            "where lr.season = :season and lr.roundSt = :roundSt and l.id = :league ")
    List<Round> findByLeagueRoundInfo(@Param("league") Long leagueId , @Param("season") int season, @Param("roundSt") int roundSt);


    /**
     * 리그의 season + roundSt 에 남아있는 경기가 있나?
     * @param season
     * @param roundSt
     * @return
     */
    @Query(" select count(r) from LeagueRound r " +
            " where r.season = :season and r.roundSt = :roundSt " +
            " and r.roundStatus <> com.example.newscoccer.domain.Round.RoundStatus.DONE ")
    Long findLeagueRemainCount(@Param("season")int season,@Param("roundSt")int roundSt);

    /**
     * 챔피언스 season + roundSt 에 남아있는 경기가 있나?
     * @param season
     * @param roundSt
     * @return
     */
    @Query(" select count(r) from ChampionsRound r " +
            " where r.season = :season and r.roundSt = :roundSt " +
            " and r.roundStatus <> com.example.newscoccer.domain.Round.RoundStatus.DONE ")
    Long findChampionsRemainCount(@Param("season")int season,@Param("roundSt")int roundSt);



    /**
     *  season 남아있는 경기가 있나?
     * @param season
     * @return
     */
    @Query(" select count(r) from Round r " +
            " where r.season = :season  " +
            " and r.roundStatus <> com.example.newscoccer.domain.Round.RoundStatus.DONE ")
    Long findRemainCount(@Param("season")int season);







}

