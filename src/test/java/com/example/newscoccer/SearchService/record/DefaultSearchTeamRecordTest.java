package com.example.newscoccer.SearchService.record;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class DefaultSearchTeamRecordTest {




    @Autowired
    SearchTeamRecord searchTeamRecord;
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
    @DisplayName("리그 -  팀 시즌 순위 조회")
    public void teamLeagueRecord() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);


        List<Team> teamList = new ArrayList<>();
        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);

            teamList.add(team);
            for(int k = 0;k<20;k++){
                Player player = Player.createPlayer("testPlayer" + i, Position.ST,team,new Stat());
                playerRepository.save(player);
            }

        }
        for(int i =  1;i<=10;i++){
            for(var t : teamList){
                LeagueRound leagueRound = new LeagueRound(league,1000,i);
                leagueRound.setRoundStatus(RoundStatus.DONE);
                roundRepository.save(leagueRound);
                TeamLeagueRecord tlr = TeamLeagueRecord.create(leagueRound,t);
                tlr.setScore(RandomNumber.returnRandomNumber(0,5));
                tlr.setRank(RandomNumber.returnRandomNumber(1,16));
                teamLeagueRecordRepository.save(tlr);
            }
        }

        // when
        SearchTeamRecordResponse resp = searchTeamRecord.searchLeagueTeamRecord(league.getId(), 1000);
        // then

        List<TeamRecordDto> resultList = resp.getResultList();
        for(int i = 0 ; i<resultList.size();i++){
            for(int k = i ; k < resultList.size();k++){
                Assertions.assertThat(resultList.get(i).getRank() <= resultList.get(k).getRank()).isTrue();
            }
        }

    }
}