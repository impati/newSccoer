package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.security.PermitAll;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}
