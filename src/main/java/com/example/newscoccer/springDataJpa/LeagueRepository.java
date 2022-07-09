package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League,Long> {
}
