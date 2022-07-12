package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    List<Team> findByLeague(League league);
    Optional<Team> findByName(String name);
}
