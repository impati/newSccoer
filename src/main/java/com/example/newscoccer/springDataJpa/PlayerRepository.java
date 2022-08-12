package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> ,PlayerRepositoryQuerydsl{
    List<Player> findByTeam(Team team);

    @Query(" select p from Player p " +
            " where p.id  in(:playerList)")
    List<Player> findByPlayerList(@Param("playerList") List<Long> playerList);
}
