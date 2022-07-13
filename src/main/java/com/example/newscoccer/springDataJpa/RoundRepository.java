package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import org.springframework.beans.factory.annotation.Autowired;
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


}
