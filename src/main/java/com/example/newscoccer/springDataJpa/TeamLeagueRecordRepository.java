package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord ,Long> {

    List<TeamLeagueRecord> findByTeam(Team team);
    List<TeamLeagueRecord> findByRound(Round round);


    /**
     *
     * @param league ,season
     * 해당 시즌에 순위대로 n개만 집합.
     *  + roundStatus.DONE 이어야함.
     */
    @Query("select tlr From TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " join fetch tlr.team t " +
            " where t.league =:league and r.roundSt = 45 and r.season = :season" +
            " order by tlr.rank ")
    List<TeamLeagueRecord> findBySeasonTopN(@Param("league") League league , @Param("season")  int season , Pageable pageable);

}
