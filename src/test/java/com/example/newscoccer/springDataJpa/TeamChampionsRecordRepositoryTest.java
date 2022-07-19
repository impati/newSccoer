package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.director.Director;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class TeamChampionsRecordRepositoryTest {
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Test
    void findTeamScoreTest() {
        Director director = directorRepository.findById(1L).orElse(null);
        List<Round> roundList = teamChampionsRecordRepository.findRoundByDirector(director.getId(),0);
        Assertions.assertThat(roundList.stream().count()).isEqualTo(2);

        for (Round round : roundList) {
            Assertions.assertThat(teamChampionsRecordRepository.findByRound(round).stream().count()).isEqualTo(2);
        }
    }
    @Test
    void findByDirector(){
        Assertions.assertThat(teamChampionsRecordRepository.findByDirector(1L,0).stream().count()).isEqualTo(2L);
    }


}