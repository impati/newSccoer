package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.PlayerLeagueRecord;
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
}
