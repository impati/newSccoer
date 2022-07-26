package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.PlayerChampionsRecord;
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

}
