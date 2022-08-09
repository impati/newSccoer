package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.dto.TeamScoreDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamChampionsRecordRepository extends JpaRepository<TeamChampionsRecord,Long> {


    @Query("select tcr from TeamChampionsRecord tcr " +
            " join fetch tcr.team t " +
            " join fetch tcr.round r " +
            " where r = :round " +
            " order by t.id")
    List<TeamChampionsRecord> findByRound(@Param("round") Round round);
    List<TeamChampionsRecord> findByDirector(Director director);



    /**
     * 시즌에 챔피언스리그 팀 기록을 감독 기준으로 찾아옴.
     */
    @Query("select tcr From TeamChampionsRecord tcr " +
            " join fetch tcr.director d " +
            " join tcr.round r " +
            " where r.season = :season and d.id = :director" +
            " order by tcr.createDate ")
    List<TeamChampionsRecord> findByDirector(@Param("director") Long director,@Param("season") int season );

    /**
     * 시즌 , 챔피언스 라운드에 진출한 팀을 가져옴.
     */
    @Query("select t from TeamChampionsRecord  tcr " +
            " join tcr.team t " +
            " join tcr.round r " +
            "where r.season = :season and r.roundSt = :roundSt")
    List<Team> findTeam(@Param("season") int season , @Param("roundSt") int roundSt);
    /**
     * 시즌 , 챔피언스 라운드에 진출한 팀 기록을 가져옴.
     */
    @Query("select tcr from TeamChampionsRecord  tcr " +
            " join tcr.team t " +
            " join tcr.round r " +
            "where r.season = :season and r.roundSt = :roundSt")
    List<TeamLeagueRecord> findBySeasonAndRoundSt(@Param("season") int season , @Param("roundSt") int roundSt);

    /**
     * 시즌 , 라운드 , 인덱스 정보 로 챔피언스 결과를 가져옴.
     * result 결과가  최대 4 개.
     */
    @Query("select tcr from TeamChampionsRecord tcr " +
            " join fetch tcr.round r " +
            " where r.season =:season and r.roundSt =:roundSt and r.index =:index")
    List<TeamChampionsRecord> findByRoundStResults (@Param("season") int season , @Param("roundSt") int roundSt , @Param("index") int index);


    /**
     *
     *  시즌,라운드 인덱스로 그 라운드에서 팀과 팀의 총 스코어를 가져옴.
     *
     */
    @Query(" select new com.example.newscoccer.springDataJpa.dto.TeamScoreDto(tcr.team,sum(tcr.score)) " +
            " from TeamChampionsRecord  tcr " +
            " join  tcr.round r " +
            " where r.season =:season and r.roundSt =:roundSt and r.index =:index " +
            " group by tcr.team ")
    List<TeamScoreDto> findTeamScore(@Param("season") int season , @Param("roundSt") int roundSt ,@Param("index") int index);


    /**
     *  directorId + season  로 round 정보 가져오기
     *  0번째 인덱스는 1라운드
     *  1번째 인덱스는 2라운드
     *
     */
    @Query("select cr from TeamChampionsRecord tcr " +
            " join tcr.round cr " +
            "  where tcr.director.id = :director and cr.season = :season" +
            " order by cr.createDate  ")
    List<Round> findRoundByDirector(@Param("director") Long directorId ,@Param("season") int season);


    /**
     * 팀 + 시즌 으로 정보를 가져옴
     */
    @Query(" select tcr from TeamChampionsRecord tcr " +
            " join tcr.round r " +
            " join tcr.team t " +
            " where r.season = :season and t.id = :team " +
            " order by tcr.createDate ")
    List<TeamChampionsRecord> findByTeamAndSeason(@Param("team") Long teamId , @Param("season") int season);


    /**
     * 팀으로 챔피언스리록을 조회
     */
    @Query(" select tcr from TeamChampionsRecord tcr " +
            " join fetch tcr.team t " +
            " join fetch tcr.round r " +
            " where t.id = :team " +
            " order by r.season ,r.roundSt ")
    List<TeamChampionsRecord> findByTeam(@Param("team") Long team);


    /**
     *  라운드 리스트에 해당되는 팀챔피언스 레코드를 모두 가져옴.
     * @param roundList
     *
     */

    @Query(" select tcr from TeamChampionsRecord  tcr " +
            " join fetch tcr.team t " +
            " join fetch  tcr.round r " +
            " where r in (:roundList) " +
            " order by t.id ")
    List<TeamChampionsRecord> findByRoundList(@Param("roundList")List<ChampionsRound> roundList);


    /**
     *
     * 라운드가 시간순으로 생성이 된다는 기준 ,
     * 팀이 참가 했던 모든 라운드를 조회 (단 , 현재 라운드 보다 이전에 치른)
     */
    @Query("select r from TeamChampionsRecord tr " +
            " join tr.round r " +
            " join tr.team t " +
            " where t = :team " +
            " and r.roundStatus = com.example.newscoccer.domain.Round.RoundStatus.DONE " +
            " and r.createDate < :create " +
            " order by tr.createDate ")
    List<Round> findRoundListByTeam(@Param("team") Team team , @Param("create") LocalDateTime create);


    /**
     *
     * roundList 에 참가한 팀중 team 에 해당하는 기록들 .
     */
    @Query(" select tcr from TeamChampionsRecord tcr " +
            " join tcr.round r " +
            " join fetch tcr.team t " +
            " where r in(:roundList) and t =:team " +
            " order by tcr.createDate desc ")
    List<TeamChampionsRecord> findByRoundListAndTeam(@Param("roundList") List<Round> roundList , @Param("team") Team team , Pageable pageable); /**
     *
     * roundList 에 참가한 팀중 team 에 해당하는 기록들 .전부
     */
    @Query(" select tcr from TeamChampionsRecord tcr " +
            " join tcr.round r " +
            " join fetch tcr.team t " +
            " where r in(:roundList) and t =:team " +
            " order by tcr.createDate desc ")
    List<TeamChampionsRecord> findAllByRoundListAndTeam(@Param("roundList") List<Round> roundList , @Param("team") Team team );



}
