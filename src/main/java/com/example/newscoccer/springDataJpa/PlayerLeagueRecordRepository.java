package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerLeagueRecordRepository extends JpaRepository<PlayerLeagueRecord ,Long> {
}
