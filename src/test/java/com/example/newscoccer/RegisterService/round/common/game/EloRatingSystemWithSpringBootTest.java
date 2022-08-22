package com.example.newscoccer.RegisterService.round.common.game;

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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@Transactional
class EloRatingSystemWithSpringBootTest {

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    EloRatingSystem eloRatingSystem;
    @Test
    @DisplayName("시즌 보상 ")
    public void seasonCompensation() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Position positions[] = new Position[]{Position.GK,Position.ST,Position.RF,Position.LF,
        Position.AM,Position.DM,Position.LM,Position.RM,Position.RB,Position.LB,Position.CB};
        List<Team> teamList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();

        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
            for(int k = 0 ;k<11;k++){
                Player player = Player.createPlayer("player " + k , positions[k],team,new Stat(70));
                playerRepository.save(player);
                playerList.add(player);
            }
        }

        // when
        int pos = 0;
        for(int i = 0;i<16;i++) {
            LeagueRound leagueRound = new LeagueRound(league, 1000, 45);
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            TeamLeagueRecord leagueRecord = TeamLeagueRecord.create(leagueRound,teamList.get(i));
            leagueRecord.setRank(i+1);
            teamLeagueRecordRepository.save(leagueRecord);
            int count = 11;
            while(count != 0 ){
                Player player = playerList.get(pos);
                PlayerLeagueRecord playerLeagueRecord = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(player,Position.ST,teamList.get(i),leagueRound);
                playerLeagueRecordRepository.save(playerLeagueRecord);
                pos += 1;
                count -= 1;
            }
        }

        eloRatingSystem.seasonCompensation(1000);

        // then


        pos = 0 ;
        int leagueCompensation[] = new int[]{50,25,12,6,2,2,1,1,0,0,0,0,-2,-2,-4,-5};
        for(int i = 0 ; i <16;i++){
            Team team = teamList.get(i);
            Assertions.assertThat(team.getRating()).isEqualTo(1500 +leagueCompensation[i]);
            int count = 11;
            while(count !=0 ){
                Assertions.assertThat(playerList.get(pos).getRating()).isEqualTo(1500 + leagueCompensation[i]);
                pos+=1;
                count -=1;
            }
        }




    }


    @Test
    @DisplayName("챔피언스 16")
    public void championsSeasonCompensation() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);

        Position positions[] = new Position[]{Position.GK,Position.ST,Position.RF,Position.LF,
                Position.AM,Position.DM,Position.LM,Position.RM,Position.RB,Position.LB,Position.CB};
        List<Team> teamList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();

        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
            for(int k = 0 ;k<11;k++){
                Player player = Player.createPlayer("player " + k , positions[k],team,new Stat(70));
                playerRepository.save(player);
                playerList.add(player);
            }
        }
        // when
        makeRecord(teamList,playerList,16);
        eloRatingSystem.seasonCompensation(1000);
        // then

        int pos = 0 ;
        int championsCompensation[] = new int[]{0,70,40,0,20,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i = 0 ; i <16;i++){
            Team team = teamList.get(i);
            Assertions.assertThat(team.getRating()).isEqualTo(1500);
            int count = 11;
            while(count !=0 ){
                Assertions.assertThat(playerList.get(pos).getRating()).isEqualTo(1500);
                pos+=1;
                count -=1;
            }
        }
    }

    @Test
    @DisplayName("챔피언스 8")
    public void championsSeasonCompensation8() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);

        Position positions[] = new Position[]{Position.GK,Position.ST,Position.RF,Position.LF,
                Position.AM,Position.DM,Position.LM,Position.RM,Position.RB,Position.LB,Position.CB};
        List<Team> teamList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();

        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
            for(int k = 0 ;k<11;k++){
                Player player = Player.createPlayer("player " + k , positions[k],team,new Stat(70));
                playerRepository.save(player);
                playerList.add(player);
            }
        }
        // when
        makeRecord(teamList,playerList,16);
        makeRecord(teamList.subList(0,8),playerList,8);
        eloRatingSystem.seasonCompensation(1000);
        // then

        int pos = 0 ;
        int championsCompensation[] = new int[]{10,10,10,10,10,10,10,10,0,0,0,0,0,0,0,0};
        for(int i = 0 ; i <16;i++){
            Team team = teamList.get(i);
            Assertions.assertThat(team.getRating()).isEqualTo(1500 + championsCompensation[i]);
            List<Player> players = playerList.stream().filter(p->p.getTeam().equals(team)).collect(Collectors.toList());
            for (Player player : players) {
                Assertions.assertThat(player.getRating()).isEqualTo(1500 + championsCompensation[i]);

            }

        }
    }

    @Test
    @DisplayName("챔피언스 4")
    public void championsSeasonCompensation4() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);

        Position positions[] = new Position[]{Position.GK,Position.ST,Position.RF,Position.LF,
                Position.AM,Position.DM,Position.LM,Position.RM,Position.RB,Position.LB,Position.CB};
        List<Team> teamList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();

        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
            for(int k = 0 ;k<11;k++){
                Player player = Player.createPlayer("player " + k , positions[k],team,new Stat(70));
                playerRepository.save(player);
                playerList.add(player);
            }
        }
        // when
        makeRecord(teamList,playerList,16);
        makeRecord(teamList.subList(0,8),playerList,8);
        makeRecord(teamList.subList(0,4),playerList,4);
        eloRatingSystem.seasonCompensation(1000);
        // then

        int pos = 0 ;
        int championsCompensation[] = new int[]{20,20,20,20,10,10,10,10,0,0,0,0,0,0,0,0};
        for(int i = 0 ; i <16;i++){
            Team team = teamList.get(i);
            Assertions.assertThat(team.getRating()).isEqualTo(1500 + championsCompensation[i]);
            List<Player> players = playerList.stream().filter(p->p.getTeam().equals(team)).collect(Collectors.toList());
            for (Player player : players) {
                Assertions.assertThat(player.getRating()).isEqualTo(1500 + championsCompensation[i]);

            }

        }
    }
    @Test
    @DisplayName("챔피언스 2")
    public void championsSeasonCompensation2() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);

        Position positions[] = new Position[]{Position.GK,Position.ST,Position.RF,Position.LF,
                Position.AM,Position.DM,Position.LM,Position.RM,Position.RB,Position.LB,Position.CB};
        List<Team> teamList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();

        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
            for(int k = 0 ;k<11;k++){
                Player player = Player.createPlayer("player " + k , positions[k],team,new Stat(70));
                playerRepository.save(player);
                playerList.add(player);
            }
        }
        // when
        makeRecord(teamList,playerList,16);
        makeRecord(teamList.subList(0,8),playerList,8);
        makeRecord(teamList.subList(0,4),playerList,4);
        makeRecord(teamList.subList(0,2),playerList,2);
        makeRecord(teamList.subList(0,1),playerList,1);

        eloRatingSystem.seasonCompensation(1000);
        // then

        int pos = 0 ;
        int championsCompensation[] = new int[]{70,40,20,20,10,10,10,10,0,0,0,0,0,0,0,0};
        for(int i = 0 ; i <16;i++){
            Team team = teamList.get(i);
            Assertions.assertThat(team.getRating()).isEqualTo(1500 + championsCompensation[i]);
            List<Player> players = playerList.stream().filter(p->p.getTeam().equals(team)).collect(Collectors.toList());
            for (Player player : players) {
                Assertions.assertThat(player.getRating()).isEqualTo(1500 + championsCompensation[i]);

            }

        }
    }


    private void makeRecord(List<Team> teamList , List<Player> playerList ,int rank){
        teamList.stream().forEach(t-> {
            ChampionsRound championsRound = new ChampionsRound(1000, rank, 1);
            championsRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(championsRound);
            TeamChampionsRecord teamChampionsRecord = TeamChampionsRecord.create(championsRound,t,1);
            teamChampionsRecord.setRank(rank);

            teamChampionsRecordRepository.save(teamChampionsRecord);
            playerList.stream().filter(p->p.getTeam().equals(t)).forEach(p->{
                PlayerChampionsRecord playerChampionsRecord =
                        (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(p,p.getPosition(),t,championsRound);
                playerChampionsRecord.setRank(rank);
                playerChampionsRecordRepository.save(playerChampionsRecord);
            }); ;
        });
    }




}