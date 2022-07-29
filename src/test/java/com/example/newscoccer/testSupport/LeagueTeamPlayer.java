package com.example.newscoccer.testSupport;


import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;

/**
 * 리그 , 팀 , 선수를 생성해서 넘겨줌 .
 * 리그 이름 : testLeague
 * 팀 이름 : testTeam
 * 선수 이름 : testPlayer , 포지션 default(ST) , stat default (value of 0 )  
 */
public class LeagueTeamPlayer {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private League league;
    private Team team;
    private Player player;


    public LeagueTeamPlayer(LeagueRepository leagueRepository, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }


    public void init(){
        league = new League("teamLeague");
        leagueRepository.save(league);
        team = Team.createTeam(league,"testTeam");
        teamRepository.save(team);
        player = Player.createPlayer("testPlayer", Position.ST,team,new Stat());
        playerRepository.save(player);
    }

    public void init(String leagueName,String teamName , String playerName){
        league = new League(leagueName);
        leagueRepository.save(league);
        team = Team.createTeam(league,teamName);
        teamRepository.save(team);
        player = Player.createPlayer(playerName, Position.ST,team,new Stat());
        playerRepository.save(player);
    }


    public League getLeague() {
        return league;
    }

    public Team getTeam() {
        return team;
    }

    public Player getPlayer() {
        return player;
    }
}

