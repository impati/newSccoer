package com.example.newscoccer.auto.lineUp;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.springDataJpa.dto.PlayerParticipate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultAutoLineUpTest {

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    AutoLineUp autoLineUp;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Test
    @DisplayName("자동 라인업 등록")
    public void autoLineUp() throws Exception {
        // given

        League league = new League("leagueTest");
        leagueRepository.save(league);
        List<Team> teamList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Team team = Team.createTeam(league, "team" + i);
            teamRepository.save(team);
            teamList.add(team);
        }

        teamList.stream().forEach(t -> {
            Player player1 = Player.createPlayer("player1", Position.ST, t, new Stat());
            Player player2 = Player.createPlayer("player2", Position.RF, t, new Stat());
            Player player3 = Player.createPlayer("player3", Position.LF, t, new Stat());
            Player player4 = Player.createPlayer("player4", Position.AM, t, new Stat());
            Player player5 = Player.createPlayer("player5", Position.RM, t, new Stat());
            Player player6 = Player.createPlayer("player6", Position.LM, t, new Stat());
            Player player7 = Player.createPlayer("player7", Position.RB, t, new Stat());
            Player player8 = Player.createPlayer("player8", Position.LB, t, new Stat());
            Player player9 = Player.createPlayer("player9", Position.CB, t, new Stat());
            Player player10 = Player.createPlayer("player10", Position.RWB, t, new Stat());
            Player player11 = Player.createPlayer("player11", Position.GK, t, new Stat());
            player1.setMain(true);
            player2.setMain(true);
            player3.setMain(true);
            player4.setMain(true);
            player5.setMain(true);
            player6.setMain(true);
            player7.setMain(true);
            player8.setMain(true);
            player9.setMain(true);
            player10.setMain(true);
            player11.setMain(true);
            playerRepository.save(player1);
            playerRepository.save(player5);
            playerRepository.save(player9);
            playerRepository.save(player2);
            playerRepository.save(player6);
            playerRepository.save(player10);
            playerRepository.save(player3);
            playerRepository.save(player7);
            playerRepository.save(player11);
            playerRepository.save(player4);
            playerRepository.save(player8);

        });

        //리그
        LeagueRound leagueRound = new LeagueRound(league, 1000, 1);
        roundRepository.save(leagueRound);

        Team teamA = teamList.get(0);
        Team teamB = teamList.get(1);

        TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound, teamA);
        TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound, teamB);
        teamLeagueRecordRepository.save(teamLeagueRecordA);
        teamLeagueRecordRepository.save(teamLeagueRecordB);

        // when
        autoLineUp.autoLineUp(leagueRound.getId());
        // then


        List<PlayerParticipate> playerParticipateListA = playerLeagueRecordRepository.findPlayerParticipate(teamA.getId(), 1000);
        assertThat(playerParticipateListA.size()).isEqualTo(11);
        for (var ele : playerParticipateListA) {
                assertThat(ele.getParticipateCount()).isEqualTo(1);
        }

        List<PlayerParticipate> playerParticipateListB = playerLeagueRecordRepository.findPlayerParticipate(teamB.getId(), 1000);
        assertThat(playerParticipateListB.size()).isEqualTo(11);
        for (var ele : playerParticipateListB) {
                assertThat(ele.getParticipateCount()).isEqualTo(1);
        }




        //챔피언스
        ChampionsRound round = new ChampionsRound(1000,16,1);
        roundRepository.save(round);


        TeamChampionsRecord tcrA = TeamChampionsRecord.create(round,teamA,1);
        TeamChampionsRecord tcrB = TeamChampionsRecord.create(round,teamB,1);
        teamChampionsRecordRepository.save(tcrA);
        teamChampionsRecordRepository.save(tcrB);

        autoLineUp.autoLineUp(round.getId());


        List<PlayerParticipate> playerParticipatesA = playerChampionsRecordRepository.findPlayerParticipate(teamA.getId(), 1000);
        assertThat(playerParticipatesA.size()).isEqualTo(11);
        for(var ele : playerParticipatesA) assertThat(ele.getParticipateCount()).isEqualTo(1);

        List<PlayerParticipate> playerParticipatesB = playerChampionsRecordRepository.findPlayerParticipate(teamB.getId(), 1000);
        assertThat(playerParticipatesB.size()).isEqualTo(11);
        for(var ele : playerParticipatesB) assertThat(ele.getParticipateCount()).isEqualTo(1);


    }

}