package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season , Long>{
}
