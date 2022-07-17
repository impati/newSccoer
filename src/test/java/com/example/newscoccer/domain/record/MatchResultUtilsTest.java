package com.example.newscoccer.domain.record;

import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MatchResultUtilsTest {

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Test
    void matchResultUtilsTest(){
        MatchResultUtils matchResultUtils = new MatchResultUtils(teamLeagueRecordRepository.findByDirectorAndSeason(1L,0));

    }
}