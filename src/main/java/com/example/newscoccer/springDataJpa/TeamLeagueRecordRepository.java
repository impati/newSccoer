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

import java.time.LocalDateTime;
import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord ,Long> {

    List<TeamLeagueRecord> findByTeam(Team team);

    @Query("select tlr from  TeamLeagueRecord tlr join tlr.team t on t.id = :team ")
    List<TeamLeagueRecord> findByTeam(@Param("team") Long teamId);

    /**
     *  라운드로 두개의 팀리그기록을 가져옴 .
     * @param round
     * @return
     */
    @Query("select tlr from TeamLeagueRecord tlr " +
            " join fetch tlr.team t " +
            " join fetch tlr.round r " +
            " where r =:round " +
            " order by t.id ")
    List<TeamLeagueRecord> findByRound(@Param("round") Round round);

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

   /**
     * 팀  + 시즌 정보로 팀리그정보를 가져옴.
     * @param teamId
     * @param season
     * @return
     */
    @Query(" select tlr from TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " join fetch tlr.team t " +
            " where r.season = :season and t.id = :team and r.roundSt < :roundSt" +
            " order by r.roundSt ")
    List<TeamLeagueRecord> findByTeamAndSeasonLessThanRoundSt(@Param("team") Long teamId , @Param("season") int season,@Param("roundSt")int roundSt);



    /**
     * 라운드 정보들로 팀 리그 정보를 가져옴 .
     */
    @Query("select tlr from TeamLeagueRecord tlr " +
            " join fetch tlr.team t " +
            " join fetch tlr.round r " +
            " where r in(:rounds) " +
            " order by t.id ")
    List<TeamLeagueRecord> findLeagueRoundInfo(@Param("rounds") List<Round> rounds);


    /**
     * 리그, 시즌에 팀들의 기록을 모두 가져와 순위를 매김.
     * @param league
     * @param season
     * @return
     */
    @Query("select tlr from TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " join tlr.team t" +
            " join t.league l " +
            " where l = :league and r.season = :season " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE " +
            " order by r.roundSt desc ")
    List<TeamLeagueRecord> findByLeagueAndSeasonForRank(@Param("league") League league ,
                                                @Param("season")int season );


    /**
     *
     * 라운드가 시간순으로 생성이 된다는 기준 ,
     * 팀이 참가 했던 모든 라운드를 조회 (단 , 현재 라운드 보다 이전에 치른)
     */
    @Query("select r from TeamLeagueRecord tr " +
            " join tr.round r " +
            " join tr.team t " +
            " where t = :team " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE " +
            " and r.createDate < :create " +
            " order by tr.createDate ")
    List<Round> findRoundListByTeam(@Param("team") Team team , @Param("create")LocalDateTime create);


    /**
     *
     * roundList 에 참가한 팀중 team 에 해당하는 기록들 .
     */
    @Query(" select tlr from TeamLeagueRecord tlr " +
            " join tlr.round r " +
            " join tlr.team t " +
            " where r in(:roundList) and t =:team " +
            " order by tlr.createDate desc ")
    List<TeamLeagueRecord> findByRoundListAndTeam(@Param("roundList") List<Round> roundList , @Param("team") Team team ,Pageable pageable);




}
