package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
