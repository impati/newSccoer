package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
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
class DefaultLineUpRegisterTest {


    @Autowired
    LineUpRegister lineUpRegister;

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
    PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;


    @Test
    @DisplayName("라인업 저장하기")
    public void lineUpRegister() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);
        Team teamA = Team.createTeam(league,"teamA");
        Team teamB = Team.createTeam(league,"teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        List<Player> playerA = makePlayer(teamA,"A");
        List<Player> playerB = makePlayer(teamB,"B");

        LeagueRound leagueRound = new LeagueRound(league,1000,1000);
        roundRepository.save(leagueRound);

        TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound,teamA);
        teamLeagueRecordRepository.save(tlrA);

         TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound,teamB);
        teamLeagueRecordRepository.save(tlrB);

        LineUpResultDto ret = new LineUpResultDto();
        ret.setRoundId(leagueRound.getId());

        for(int i = 0 ;i<11;i++){
            Player player = playerA.get(i);
            ret.getParticipatePlayer().add(new LineUpResult(player.getId(),player.getPosition()));
        }

        for(int i = 1 ;i<=11;i++){
            Player player = playerB.get(i);
            ret.getParticipatePlayer().add(new LineUpResult(player.getId(),Position.GK));
        }


        // when
        lineUpRegister.lineUpRegister(ret);


        List<PlayerLeagueRecord> resultA = playerLeagueRecordRepository.findByTeamAndRound(teamA,leagueRound);
        List<PlayerLeagueRecord> resultB = playerLeagueRecordRepository.findByTeamAndRound(teamB,leagueRound);
        // then


        assertThat(resultA.size()).isEqualTo(11);
        assertThat(resultB.size()).isEqualTo(11);


        // 조금 더 구체적인 예시
        // A 는 자기 팀으로 B 는 GK로 참가

        resultA.stream().forEach(p->{
            assertThat(p.getPlayer().getPosition()).isEqualTo(p.getPosition());
        });

        resultB.stream().forEach(p->{
            assertThat(Position.GK).isEqualTo(p.getPosition());
        });






    }



    private List<Player> makePlayer(Team team, String deli){
        Position position[] = {Position.ST,Position.RF,Position.LF,
            Position.AM , Position.RM , Position.LM,Position.DM,
            Position.LB,Position.RB,Position.CB,
            Position.GK,Position.CF
        };
        List<Player> resp = new ArrayList<>();
        for(int i = 0 ;i<12;i++){
            Player player = Player.createPlayer("player" + i + deli ,position[i],team,new Stat());
            resp.add(player);
            playerRepository.save(player);
        }

        return resp;
    }
}