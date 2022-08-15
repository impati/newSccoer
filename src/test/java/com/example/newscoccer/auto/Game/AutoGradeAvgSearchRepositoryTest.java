package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AutoGradeAvgSearchRepositoryTest {

    @Autowired
    AutoGradeAvgSearchRepository repository;

    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RoundRepository roundRepository;

    @Test
    @DisplayName("평균조회")
    public void avg() throws Exception{

        League league = new League("leagueTest");
        leagueRepository.save(league);
        Team team = Team.createTeam(league,"teamTest");
        teamRepository.save(team);

        for(int i = 0; i <10; i++){
            Player player = Player.createPlayer("testPlayer" + i , Position.ST,team,new Stat());
            playerRepository.save(player);
            LeagueRound leagueRound = new LeagueRound(league,10000,i);
            roundRepository.save(leagueRound);
            PlayerLeagueRecord plr = (PlayerLeagueRecord)PlayerLeagueRecord.createPlayerRecord(player,Position.ST,team,leagueRound);
            plr.setFoul(1);
            plr.setPass(20);
            plr.setShooting(5);
            plr.setValidShooting(2);
            plr.setGoodDefense(50);
            plr.setGrade(100);
            playerLeagueRecordRepository.save(plr);
        }

        for(int i =0;i<10;i++){
            Player player = Player.createPlayer("testPlayer" + i , Position.ST,team,new Stat());
            playerRepository.save(player);
            ChampionsRound championsRound = new ChampionsRound(10000,i,1);
            roundRepository.save(championsRound);
            PlayerChampionsRecord pcr = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(player,Position.ST,team,championsRound);

            pcr.setFoul(100);
            pcr.setPass(100);
            pcr.setShooting(500);
            pcr.setValidShooting(20);
            pcr.setGoodDefense(500);
            pcr.setGrade(100);
            playerChampionsRecordRepository.save(pcr);
        }

        GradeDto dto = repository.recordAvg();

        System.out.println("dto = " + dto);





    }
}