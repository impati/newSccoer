package com.example.newscoccer.SearchService.round.common;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultRoundLineUpTest {


    @Autowired
    RoundLineUp roundLineUp;

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

    @Test
    @DisplayName("리그 라운드 라인업")
    public void leagueLineUp() throws Exception{
        // given

        Long roundId = makeTeamLeagueRound();

        // when
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));

        // then
        Assertions.assertThat(resp.getTeamAName()).isEqualTo("testTeam");
        Assertions.assertThat(resp.getTeamBName()).isEqualTo("oppositeTestTeam");

        Assertions.assertThat(resp.getPlayerListA().size()).isEqualTo(12);
        Assertions.assertThat(resp.getPlayerListB().size()).isEqualTo(11);


    }



    private Long makeTeamLeagueRound(){


        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();
        League league = maker.getLeague();
        Team team = maker.getTeam();

        Team opposite = Team.createTeam(league,"oppositeTestTeam");
        teamRepository.save(opposite);

        for(int i = 1;i<=11;i+=1) {
            Player player = Player.createPlayer("test" + i, Position.ST, team, new Stat());
            Player oppositePlayer = Player.createPlayer("oppositeTest" + i, Position.GK, opposite, new Stat());

            playerRepository.save(oppositePlayer);
            playerRepository.save(player);


        }



            LeagueRound round = new LeagueRound(league,0,1000);

            roundRepository.save(round);

            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(round,team);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(round,opposite);

            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);




            return round.getId();




    }












}