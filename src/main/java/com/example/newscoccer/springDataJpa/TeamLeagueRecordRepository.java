package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord ,Long> {

    List<TeamLeagueRecord> findByTeam(Team team);
    List<TeamLeagueRecord> findByRound(Round round);
    List<TeamLeagueRecord> findByDirector(Director director);

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


    /**
     * 감독 + 시즌 정보로 팀 정보를 모두 가져옴 .
     * @return
     */
    @Query("select tlr from TeamLeagueRecord tlr " +
            " join  fetch tlr.director d" +
            " join tlr.round r " +
            " where d.id = :director and r.season = :season " +
            " order by tlr.createDate ")
    List<TeamLeagueRecord> findByDirectorAndSeason(@Param("director") Long directorId ,@Param("season") int season);


    /**
     * 감독의 정보로 팀의 시즌 마지막 경기들을 가져옴 .
     * @param director
     * @return
     */
    @Query("select tlr from TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " where tlr.director = :director " +
            " and r.roundSt = 45 " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE ")
    List<TeamLeagueRecord> findBySeasonLastGame(@Param("director") Director director);


    /**
     * 팀  + 시즌 정보로 팀리그정보를 가져옴.
     * @param teamId
     * @param season
     * @return
     */
    @Query(" select tlr from TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " join fetch tlr.team t " +
            " where r.season = :season and t.id = :team " +
            " order by r.roundSt ")
    List<TeamLeagueRecord> findByTeamAndSeason(@Param("team") Long teamId , @Param("season") int season);

}
