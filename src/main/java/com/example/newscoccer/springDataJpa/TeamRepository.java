package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    List<Team> findByLeague(League league);
    @Query("select t from Team t join t.league l where l.id = :league " +
            " order by t.rating desc ")
    List<Team> findByLeague(@Param("league") Long league);
    Optional<Team> findByName(String name); // exact 매칭

}
