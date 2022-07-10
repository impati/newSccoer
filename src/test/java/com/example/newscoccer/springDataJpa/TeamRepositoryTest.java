package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TeamRepositoryTest {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    LeagueRepository leagueRepository;

    @Test
    @DisplayName("리그로 팀찾기")
    void findByLeagueTest(){
        Assertions.assertThat(teamRepository.findAll().stream().count()).isEqualTo(64);
        List<League> all = leagueRepository.findAll();
        Assertions.assertThat(teamRepository.findByLeague(all.get(0)).stream().count()).isEqualTo(16);

    }
}