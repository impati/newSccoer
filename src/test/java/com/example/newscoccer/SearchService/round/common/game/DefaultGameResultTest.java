package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUp;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultGameResultTest {


    @Autowired
    GameResult gameResult;
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

    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;

    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Test
    @DisplayName("리그 경기 종료 전")
    public void gameResultBefore() throws Exception{
        // given
        LeagueRound round = getLeagueRound();


        // when

        GameResultResponse resp  = gameResult.gameResult(new GameResultRequest(round.getId()));


        // then
        assertThat(resp.getIsDone()).isFalse();
        assertThat(resp.getTeamA().getTeamName()).isEqualTo("testTeam1");
        assertThat(resp.getTeamA().getCornerKick()).isEqualTo(0);
        assertThat(resp.getTeamA().getScore()).isEqualTo(0);
        assertThat(resp.getTeamA().getFreeKick()).isEqualTo(0);
        assertThat(resp.getTeamA().getShare()).isEqualTo(0);


        assertThat(resp.getTeamB().getTeamName()).isEqualTo("testTeam2");



        assertThat(resp.getPlayerADtoList().size()).isEqualTo(11);

        assertThat(resp.getPlayerBDtoList().get(5).getAssist()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getGoal()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getDefense()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getFoul()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getPass()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getGrade()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getShooting()).isEqualTo(0);
        assertThat(resp.getPlayerBDtoList().get(5).getValidShooting()).isEqualTo(0);

        assertThat(resp.getPlayerBDtoList().size()).isEqualTo(11);


    }

    private LeagueRound getLeagueRound() {
        League league = new League("testLeague1");

        leagueRepository.save(league);


        Team team1 = Team.createTeam(league,"testTeam1");
        Team team2 = Team.createTeam(league,"testTeam2");


        teamRepository.save(team1);
        teamRepository.save(team2);

        for(int i = 1;i<=11;i+=1) {
            Player player = Player.createPlayer("testPlayer1" + i, Position.ST, team1, new Stat());
            Player oppositePlayer = Player.createPlayer("testPlayer2" + i, Position.GK, team2, new Stat());

            playerRepository.save(oppositePlayer);
            playerRepository.save(player);

        }


        LeagueRound round = new LeagueRound(league,1111,2000);
        roundRepository.save(round);

        TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(round,team1);
        TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(round,team2);

        teamLeagueRecordRepository.save(teamLeagueRecordA);
        teamLeagueRecordRepository.save(teamLeagueRecordB);


        playerRepository.findByTeam(team1).stream().forEach(p->{
            PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(p,p.getPosition(),team1,round);
            playerLeagueRecordRepository.save(plr);
        });
        playerRepository.findByTeam(team2).stream().forEach(p->{
            PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(p,p.getPosition(),team2,round);
            playerLeagueRecordRepository.save(plr);

        });
        return round;
    }

    @Test
    @DisplayName("리그 경기 종료 후")
    public void gameResult() throws Exception{
        // given
        Long roundId = makeTeamChampionsRound();
        // when
        GameResultResponse resp = gameResult.gameResult(new GameResultRequest(roundId));
        // then

        assertThat(resp.getIsDone()).isTrue();
        assertThat(resp.getTeamA().getTeamName()).isEqualTo("testTeam1");
        assertThat(resp.getTeamA().getCornerKick()).isEqualTo(5);
        assertThat(resp.getTeamA().getScore()).isEqualTo(5);
        assertThat(resp.getTeamA().getFreeKick()).isEqualTo(5);
        assertThat(resp.getTeamA().getShare()).isEqualTo(5);


        assertThat(resp.getTeamB().getTeamName()).isEqualTo("testTeam2");



        assertThat(resp.getPlayerADtoList().size()).isEqualTo(11);

        assertThat(resp.getPlayerBDtoList().get(5).getAssist()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getGoal()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getDefense()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getFoul()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getPass()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getGrade()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getShooting()).isEqualTo(2);
        assertThat(resp.getPlayerBDtoList().get(5).getValidShooting()).isEqualTo(2);

        assertThat(resp.getPlayerBDtoList().size()).isEqualTo(11);


    }


    private Long makeTeamChampionsRound(){

        League league1 = new League("testLeague1");
        League league2 = new League("testLeague2");


        leagueRepository.save(league1);
        leagueRepository.save(league2);

        Team team1 = Team.createTeam(league1,"testTeam1");
        Team team2 = Team.createTeam(league2,"testTeam2");


        teamRepository.save(team1);
        teamRepository.save(team2);

        for(int i = 1;i<=11;i+=1) {
            Player player = Player.createPlayer("testPlayer1" + i, Position.ST, team1, new Stat());
            Player oppositePlayer = Player.createPlayer("testPlayer2" + i, Position.GK, team2, new Stat());

            playerRepository.save(oppositePlayer);
            playerRepository.save(player);

        }



        ChampionsRound round = new ChampionsRound(0,1000,2000);
        roundRepository.save(round);

        TeamChampionsRecord teamLeagueRecordA = TeamChampionsRecord.create(round,team1,1);
        TeamChampionsRecord teamLeagueRecordB = TeamChampionsRecord.create(round,team2,1);

        teamLeagueRecordA.setScore(5);
        teamLeagueRecordA.setShare(5);
        teamLeagueRecordA.setCornerKick(5);
        teamLeagueRecordA.setFreeKick(5);


        teamLeagueRecordB.setScore(2);
        teamLeagueRecordB.setShare(22);
        teamLeagueRecordB.setCornerKick(2);
        teamLeagueRecordB.setCornerKick(2);


        teamChampionsRecordRepository.save(teamLeagueRecordA);
        teamChampionsRecordRepository.save(teamLeagueRecordB);


        playerRepository.findByTeam(team1).stream().forEach(p->{
            PlayerChampionsRecord pcr = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(p,p.getPosition(),team1,round);
            pcr.setGoal(5);
            pcr.setAssist(5);
            pcr.setFoul(5);
            pcr.setValidShooting(5);
            pcr.setShooting(5);
            pcr.setGoodDefense(5);



            playerChampionsRecordRepository.save(pcr);
        });
        playerRepository.findByTeam(team2).stream().forEach(p->{
            PlayerChampionsRecord pcr = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(p,p.getPosition(),team2,round);
            pcr.setGoal(2);
            pcr.setAssist(2);
            pcr.setGrade(2);
            pcr.setFoul(2);
            pcr.setValidShooting(2);
            pcr.setShooting(2);
            pcr.setGoodDefense(2);
            pcr.setPass(2);

            playerChampionsRecordRepository.save(pcr);

        });

        round.setRoundStatus(RoundStatus.DONE);
        return round.getId();




    }



}