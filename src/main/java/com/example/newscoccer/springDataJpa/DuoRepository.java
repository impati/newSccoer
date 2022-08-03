package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.Duo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuoRepository extends JpaRepository<Duo,Long> {
}
