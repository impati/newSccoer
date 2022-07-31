package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.springDataJpa.dto.PlayerParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerChampionsRecordRepository extends JpaRepository<PlayerChampionsRecord,Long> {


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
     * 팀 + 라운드로 해당 라운드에 참가한 선수들을 가져옴 .
     */
    @Query("select pcr from PlayerChampionsRecord pcr " +
            " join pcr.team t " +
            " join pcr.round r " +
            " where t = :team and r = :round")
    List<PlayerChampionsRecord> findByTeamAndRound(@Param("team") Team team , @Param("round") Round round);


}
