package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpRegister;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResult;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResultDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import org.assertj.core.api.Assertions;
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
class
DefaultGameRecordRegisterTest {

    @Autowired
    GameRecordRegister gameRecordRegister;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;

    @Autowired
    LineUpRegister lineUpRegister;
    @Test
    @DisplayName("리그 경기 기록 저장")
    public void leagueGameRecordRegister() throws Exception{
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
            ret.getParticipatePlayer().add(new LineUpResult(player.getId(), Position.GK));
        }


        lineUpRegister.lineUpRegister(ret);


        GameRecordDto gameRecordDto = new GameRecordDto();
        gameRecordDto.setRoundId(leagueRound.getId());
        GameResultTeamDto teamDtoA = new GameResultTeamDto(teamA.getId(), teamA.getName());

        teamDtoA.setShare(5);
        teamDtoA.setScore(5);
        teamDtoA.setCornerKick(5);
        teamDtoA.setFreeKick(5);
        gameRecordDto.getTeams().add(teamDtoA);
        GameResultTeamDto teamDtoB = new GameResultTeamDto(teamB.getId(),teamB.getName());
        teamDtoB.setShare(2);
        teamDtoB.setScore(2);
        teamDtoB.setCornerKick(2);
        teamDtoB.setFreeKick(2);
        gameRecordDto.getTeams().add(teamDtoB);



        playerLeagueRecordRepository.findByTeamAndRound(teamA,leagueRound).stream().forEach(p->{
            gameRecordDto.getPlayers().add(new GameResultPlayerDto(p.getPlayer().getId(),p.getPlayer().getName(),
                    p.getPosition(),5,5,5,5,5,5,5,5));
        });

        playerLeagueRecordRepository.findByTeamAndRound(teamB,leagueRound).stream().forEach(p->{
            gameRecordDto.getPlayers().add(new GameResultPlayerDto(p.getPlayer().getId(),p.getPlayer().getName(),
                    p.getPosition(),2,2,2,2,2,2,2,2));
        });
        // when
        gameRecordRegister.gameRecordRegister(gameRecordDto);
        // then


        List<TeamLeagueRecord> byRound = teamLeagueRecordRepository.findByRound(leagueRound);
        assertThat(byRound.get(0).getScore()).isEqualTo(5);
        assertThat(byRound.get(0).getShare()).isEqualTo(5);
        assertThat(byRound.get(0).getCornerKick()).isEqualTo(5);
        assertThat(byRound.get(0).getFreeKick()).isEqualTo(5);

        assertThat(byRound.get(1).getScore()).isEqualTo(2);
        assertThat(byRound.get(1).getShare()).isEqualTo(2);
        assertThat(byRound.get(1).getCornerKick()).isEqualTo(2);
        assertThat(byRound.get(1).getFreeKick()).isEqualTo(2);


        playerLeagueRecordRepository.findByTeamAndRound(teamA,leagueRound).stream().forEach(p->{
            assertThat(p.getGoal()).isEqualTo(5);
            assertThat(p.getAssist()).isEqualTo(5);
            assertThat(p.getPass()).isEqualTo(5);
            assertThat(p.getShooting()).isEqualTo(5);
            assertThat(p.getValidShooting()).isEqualTo(5);
            assertThat(p.getFoul()).isEqualTo(5);
            assertThat(p.getGoodDefense()).isEqualTo(5);
            assertThat(p.getGrade()).isEqualTo(5);
        });
        playerLeagueRecordRepository.findByTeamAndRound(teamB,leagueRound).stream().forEach(p->{
            assertThat(p.getGoal()).isEqualTo(2);
            assertThat(p.getAssist()).isEqualTo(2);
            assertThat(p.getPass()).isEqualTo(2);
            assertThat(p.getShooting()).isEqualTo(2);
            assertThat(p.getValidShooting()).isEqualTo(2);
            assertThat(p.getFoul()).isEqualTo(2);
            assertThat(p.getGoodDefense()).isEqualTo(2);
            assertThat(p.getGrade()).isEqualTo(2);
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

    @Test
    @DisplayName("리그 경기 기록 저장 - 세부 테스트")
    public void more() throws Exception{
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
            ret.getParticipatePlayer().add(new LineUpResult(player.getId(), Position.GK));
        }


        lineUpRegister.lineUpRegister(ret);


        GameRecordDto gameRecordDto = new GameRecordDto();
        gameRecordDto.setRoundId(leagueRound.getId());
        GameResultTeamDto teamDtoA = new GameResultTeamDto(teamA.getId(), teamA.getName());

        teamDtoA.setShare(5);
        teamDtoA.setScore(5);
        teamDtoA.setCornerKick(5);
        teamDtoA.setFreeKick(5);
        gameRecordDto.getTeams().add(teamDtoA);
        GameResultTeamDto teamDtoB = new GameResultTeamDto(teamB.getId(),teamB.getName());
        teamDtoB.setShare(2);
        teamDtoB.setScore(2);
        teamDtoB.setCornerKick(2);
        teamDtoB.setFreeKick(2);
        gameRecordDto.getTeams().add(teamDtoB);



        playerLeagueRecordRepository.findByTeamAndRound(teamA,leagueRound).stream().forEach(p->{
            gameRecordDto.getPlayers().add(new GameResultPlayerDto(p.getPlayer().getId(),p.getPlayer().getName(),
                    p.getPosition(),5,5,5,5,5,5,5,5));
        });

        playerLeagueRecordRepository.findByTeamAndRound(teamB,leagueRound).stream().forEach(p->{
            gameRecordDto.getPlayers().add(new GameResultPlayerDto(p.getPlayer().getId(),p.getPlayer().getName(),
                    p.getPosition(),2,2,2,2,2,2,2,2));
        });
        // when
        gameRecordRegister.gameRecordRegister(gameRecordDto);
        // then

        List<TeamLeagueRecord> resultTeamList = teamLeagueRecordRepository.findByRound(leagueRound);

        assertThat(resultTeamList.size()).isEqualTo(2);
        TeamLeagueRecord teamLeagueRecordA = resultTeamList.get(0);
        TeamLeagueRecord teamLeagueRecordB = resultTeamList.get(1);

        // matchResult 테스트
        assertThat(teamLeagueRecordA.getMatchResult()).isEqualTo(MatchResult.WIN);
        assertThat(teamLeagueRecordB.getMatchResult()).isEqualTo(MatchResult.LOSE);
        // 평균 평점
        assertThat(teamLeagueRecordA.getGrade()).isEqualTo(5.0);
        assertThat(teamLeagueRecordB.getGrade()).isEqualTo(2.0);

        // 평균 슈팅
        assertThat(teamLeagueRecordA.getShooting()).isEqualTo(55);
        assertThat(teamLeagueRecordB.getShooting()).isEqualTo(22);



        List<PlayerLeagueRecord> playerLeagueRecordsA = playerLeagueRecordRepository.findByTeamAndRound(teamA,leagueRound);
        Assertions.assertThat(playerLeagueRecordsA.size()).isEqualTo(11);

        for (var record : playerLeagueRecordsA) {
            assertThat(record.getGoodDefense()).isEqualTo(5);
            assertThat(record.getFoul()).isEqualTo(5);
            assertThat(record.getShooting()).isEqualTo(5);
            assertThat(record.getValidShooting()).isEqualTo(5);
            assertThat(record.getGoal()).isEqualTo(5);
            assertThat(record.getAssist()).isEqualTo(5);
            assertThat(record.getGrade()).isEqualTo(5);
            assertThat(record.getMatchResult()).isEqualTo(MatchResult.WIN);
            assertThat(record.isBest()).isTrue();
        }


        List<PlayerLeagueRecord> playerLeagueRecordsB = playerLeagueRecordRepository.findByTeamAndRound(teamB,leagueRound);
        Assertions.assertThat(playerLeagueRecordsB.size()).isEqualTo(11);

        for (var record : playerLeagueRecordsB) {
            assertThat(record.getGoodDefense()).isEqualTo(2);
            assertThat(record.getFoul()).isEqualTo(2);
            assertThat(record.getShooting()).isEqualTo(2);
            assertThat(record.getValidShooting()).isEqualTo(2);
            assertThat(record.getGoal()).isEqualTo(2);
            assertThat(record.getAssist()).isEqualTo(2);
            assertThat(record.getGrade()).isEqualTo(2);
            assertThat(record.getMatchResult()).isEqualTo(MatchResult.LOSE);
            assertThat(record.isBest()).isTrue();
        }

    }


}