package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round,Long> {
}
