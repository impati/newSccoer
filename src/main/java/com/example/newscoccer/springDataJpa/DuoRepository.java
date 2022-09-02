package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.Duo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DuoRepository extends JpaRepository<Duo,Long> {


    @Query("select d from Duo d join fetch d.round r where r.id = :round ")
    List<Duo> findByRound(@Param("round") Long roundId);

}
