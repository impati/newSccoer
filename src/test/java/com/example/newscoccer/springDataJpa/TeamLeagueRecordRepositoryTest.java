package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamLeagueRecordRepositoryTest {

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamRepository teamRepository;
    @Test
    void findBySeasonTopNTest(){

        leagueRepository.findAll().stream().forEach(league->{
            List<Team> teamList = teamRepository.findByLeague(league);
                int r = 1;
                for(var team : teamList) {
                    List<TeamLeagueRecord> teamRecordResult = teamLeagueRecordRepository.findByTeam(team);
                    for (TeamLeagueRecord teamLeagueRecord : teamRecordResult) {
                        if (teamLeagueRecord.getRound().getRoundSt() == 45) {
                            teamLeagueRecord.setRank(r);
                            r++;
                        }
                    }

                }
            List<TeamLeagueRecord> ret = teamLeagueRecordRepository.findBySeasonTopN(league, 0, PageRequest.of(0, 4));
            Assertions.assertThat(ret.size()).isEqualTo(4);
            for(int i =0 ;i<4;i++){
                Assertions.assertThat(ret.get(i).getRank()).isEqualTo(i+1);
            }

        });

    }
}