package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.TeamRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;
    @Test
    void findByTeam(){
        teamRepository.findAll().stream().forEach(team ->{
            Assertions.assertThat(playerRepository.findByTeam(team).stream().count()).isGreaterThanOrEqualTo(11);
        });

    }
}