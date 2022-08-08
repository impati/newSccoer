package com.example.newscoccer.springDataJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@Transactional
class PlayerChampionsRepositoryQuerydslImplTest {



    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    PlayerRepository playerRepository;



}
