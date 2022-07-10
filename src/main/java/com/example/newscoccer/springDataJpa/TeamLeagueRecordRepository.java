package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord ,Long> {

    List<TeamLeagueRecord> findByTeam(Team team);
    List<TeamLeagueRecord> findByRound(Round round);
}
