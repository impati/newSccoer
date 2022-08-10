package com.example.newscoccer.testSupport;


import com.example.newscoccer.NewScoccerApplication;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;


public class TestController {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final RoundRepository roundRepository;
    public TestController() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(NewScoccerApplication.class);

        this.leagueRepository = (LeagueRepository) ac.getBean("leagueRepository");
        this.teamRepository = (TeamRepository) ac.getBean("teamRepository");
        this.playerRepository = (PlayerRepository) ac.getBean("playerRepository");
        this.playerLeagueRecordRepository = (PlayerLeagueRecordRepository) ac.getBean("playerLeagueRecordRepository");
        this.playerChampionsRecordRepository = (PlayerChampionsRecordRepository) ac.getBean("playerChampionsRecordRepository");
        this.teamChampionsRecordRepository = (TeamChampionsRecordRepository) ac.getBean("teamChampionsRecordRepository");;
        this.teamLeagueRecordRepository = (TeamLeagueRecordRepository) ac.getBean("teamLeagueRecordRepository");;
        this.roundRepository = (RoundRepository) ac.getBean("roundRepository");

    }


    public League createLeague(){
        League league = new League("testLeague");
        leagueRepository.save(league);
        return league;
    }

    public Team createTeam(String teamName){
        Team team = Team.createTeam(createLeague(),"testTeam");
        teamRepository.save(team);
        return team;
    }
    public Player createPlayer (String playerName,Team team){
        Player player = Player.createPlayer("testPlayer", Position.ST,team,new Stat());
        playerRepository.save(player);
        return player;
    }
    public List<Team> createTeamList(int n){
        League league = createLeague();
        List<Team> ret = new ArrayList<>();
        for(int i = 0; i < n ;i++){
            ret.add(createTeam("testTeam" + i));
        }
        return ret;
    }
    public List<Player> createPlayerList(Team team , int n){
        List<Player> ret = new ArrayList<>();
        for(int i  =0;i<n;i++){
            ret.add(createPlayer("testPlayer" + i,team));
        }
        return ret;
    }











    // getter
    public LeagueRepository getLeagueRepository() {

        return leagueRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public PlayerLeagueRecordRepository getPlayerLeagueRecordRepository() {
        return playerLeagueRecordRepository;
    }

    public PlayerChampionsRecordRepository getPlayerChampionsRecordRepository() {
        return playerChampionsRecordRepository;
    }

    public TeamChampionsRecordRepository getTeamChampionsRecordRepository() {
        return teamChampionsRecordRepository;
    }

    public TeamLeagueRecordRepository getTeamLeagueRecordRepository() {
        return teamLeagueRecordRepository;
    }

    public RoundRepository getRoundRepository() {
        return roundRepository;
    }
}
