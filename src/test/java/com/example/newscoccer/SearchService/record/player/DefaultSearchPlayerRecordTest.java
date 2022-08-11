package com.example.newscoccer.SearchService.record.player;

import com.example.newscoccer.domain.Direction;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.SortType;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
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
class DefaultSearchPlayerRecordTest {

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    SearchPlayerRecord searchPlayerRecord;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Test
    @DisplayName("리그 - 선수 기록 순위")
    public void league() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        List<Team> teamList = new ArrayList<>();

        for(int i = 0;i<16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);
            teamList.add(team);
        }
        List<Player> playerList =  createPlayerList(teamList);


        for(int i = 1;i<=45;i++){
            LeagueRound leagueRound = new LeagueRound(league,1000,i);
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            for(int k = 0;k<playerList.size();k++){
                Player player = playerList.get(k);
                PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord( player,Position.ST,player.getTeam(),leagueRound);
                plr.setRank(RandomNumber.returnRandomNumber(1,16));
                plr.setGoal(RandomNumber.returnRandomNumber(1,16));
                plr.setAssist(RandomNumber.returnRandomNumber(1,16));
                plr.setShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setValidShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setFoul(RandomNumber.returnRandomNumber(1,16));
                plr.setPass(RandomNumber.returnRandomNumber(1,16));
                plr.setGoodDefense(RandomNumber.returnRandomNumber(1,16));
                playerLeagueRecordRepository.save(plr);
            }

        }
        // when


        List<PlayerRecordDto> resultList = searchPlayerRecord.searchLeaguePlayerRecord(league.getId(), 1000, SortType.GOAL, Direction.DESC).getResultList();
        // then
        assertThat(resultList.size()).isEqualTo(12*16);
        assertGoalDESC(resultList);
        assertGoalASC(searchPlayerRecord.searchLeaguePlayerRecord(league.getId(),1000,SortType.GOAL,Direction.ASC).getResultList());
        assertPassDESC(searchPlayerRecord.searchLeaguePlayerRecord(league.getId(), 1000, SortType.PASS, Direction.DESC).getResultList());

    }
    private void assertPassDESC(List<PlayerRecordDto> resultList){
        for(int i = 0; i< resultList.size(); i++){
            for(int k = i; k< resultList.size(); k++){
                assertThat(resultList.get(i).getPass() >= resultList.get(k).getPass());
            }
        }
    }

    private void assertGoalDESC(List<PlayerRecordDto> resultList) {
        for(int i = 0; i< resultList.size(); i++){
            for(int k = i; k< resultList.size(); k++){
                assertThat(resultList.get(i).getGoal() >= resultList.get(k).getGoal());
            }
        }
    }
    private void assertGoalASC(List<PlayerRecordDto> resultList) {
        for(int i = 0; i< resultList.size(); i++){
            for(int k = i; k< resultList.size(); k++){
                assertThat(resultList.get(i).getGoal() <= resultList.get(k).getGoal());
            }
        }
    }



    @Test
    @DisplayName("챔피언스 - 선수 기록 순위")
    public void champions() throws Exception{
        // given

        League league1  = new League("league1");
        League league2  = new League("league2");

        leagueRepository.save(league1);
        leagueRepository.save(league2);


        List<Team> teamListA = new ArrayList<>();
        List<Team> teamListB = new ArrayList<>();

        for(int i = 0;i<16;i++){
            Team team = Team.createTeam(league1,"testTeamA" + i);
            Team team2 = Team.createTeam(league2,"testTeamB" + i);
            teamRepository.save(team);
            teamRepository.save(team2);
            teamListA.add(team);
            teamListB.add(team);
        }
        List<Player> playerListA = createPlayerList(teamListA);
        List<Player> playerListB = createPlayerList(teamListB);


        for(int i = 1;i<=45;i++){
            ChampionsRound championsRound = new ChampionsRound(1000,i,1);
            championsRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(championsRound);
            for(int k = 0;k<playerListA.size();k++){
                Player player = playerListA.get(k);
                teamChampionsRecordRepository.save(TeamChampionsRecord.create(championsRound,player.getTeam(),1));
                PlayerChampionsRecord plr = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord( player,Position.ST,player.getTeam(),championsRound);
                plr.setRank(RandomNumber.returnRandomNumber(1,16));
                plr.setGoal(RandomNumber.returnRandomNumber(1,16));
                plr.setAssist(RandomNumber.returnRandomNumber(1,16));
                plr.setShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setValidShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setFoul(RandomNumber.returnRandomNumber(1,16));
                plr.setPass(RandomNumber.returnRandomNumber(1,16));
                plr.setGoodDefense(RandomNumber.returnRandomNumber(1,16));
                playerChampionsRecordRepository.save(plr);
            }

        }
        for(int i = 1;i<=45;i++){
            ChampionsRound championsRound = new ChampionsRound(1000,i,1);
            championsRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(championsRound);
            for(int k = 0;k<playerListB.size();k++){
                Player player = playerListB.get(k);
                teamChampionsRecordRepository.save(TeamChampionsRecord.create(championsRound,player.getTeam(),1));
                PlayerChampionsRecord plr = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord( player,Position.ST,player.getTeam(),championsRound);
                plr.setRank(RandomNumber.returnRandomNumber(1,16));
                plr.setGoal(RandomNumber.returnRandomNumber(1,16));
                plr.setAssist(RandomNumber.returnRandomNumber(1,16));
                plr.setShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setValidShooting(RandomNumber.returnRandomNumber(1,16));
                plr.setFoul(RandomNumber.returnRandomNumber(1,16));
                plr.setPass(RandomNumber.returnRandomNumber(1,16));
                plr.setGoodDefense(RandomNumber.returnRandomNumber(1,16));
                playerChampionsRecordRepository.save(plr);
            }

        }


        // when


        List<PlayerRecordDto> resultList = searchPlayerRecord.searchChampionsPlayerRecord( 1000, SortType.GOAL, Direction.DESC).getResultList();

        // then
        assertThat(resultList.size()).isEqualTo(32 * 12);
        assertGoalDESC(resultList);

        assertGoalASC(searchPlayerRecord.searchChampionsPlayerRecord(1000,SortType.GOAL,Direction.ASC).getResultList());
        assertPassDESC(searchPlayerRecord.searchChampionsPlayerRecord( 1000, SortType.PASS, Direction.DESC).getResultList());

        // then

    }

    private List<Player> createPlayerList(List<Team> teamListA) {
        List<Player> playerList = new ArrayList<>();
        for(int i = 0; i< teamListA.size(); i++){
            for(int k =0;k<12;k++){
                Player player = Player.createPlayer("testPlayer" + i + k, Position.ST, teamListA.get(i),new Stat());
                playerRepository.save(player);
                playerList.add(player);
            }
        }
        return playerList;
    }


}