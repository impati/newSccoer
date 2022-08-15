package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class LeagueRoundGeneratorImplTest {
    @Autowired
    private  RoundRepository roundRepository;
    @Autowired
    private  TeamRepository teamRepository;
    @Autowired
    private  LeagueRepository leagueRepository;
    @Autowired
    private  TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    private LeagueRoundGenerator leagueRoundGenerator;
    @Test
    void test(){
        //THEN
        Team team = teamRepository.findById(1L).get();
        Assertions.assertThat(teamLeagueRecordRepository.findByTeam(team).stream().count()).isEqualTo(45);
    }
    @Test
    void generatorTest(){


        League league = leagueRepository.findById(1L).get();
        List<Team> teamList = teamRepository.findByLeague(league);
        List<TeamLeagueRecord> ret = new ArrayList<>();

        for(var team : teamList){
            List<TeamLeagueRecord> byTeam = teamLeagueRecordRepository.findByTeam(team);
            Assertions.assertThat(byTeam.stream().count()).isEqualTo(45);
            for (var ele : byTeam) {
                ret.add(ele);
            }
        }

        int visited[][] = new int[100][100];
        roundRepository.findAll().stream().filter(round -> round instanceof LeagueRound).forEach(round->{

            Assertions.assertThat(round).isInstanceOf(LeagueRound.class);
            List<TeamLeagueRecord>  temp = teamLeagueRecordRepository.findByRound(round);

            Assertions.assertThat(temp.size()).isEqualTo(2);

            int l = Math.toIntExact(temp.get(0).getTeam().getId());
            int r = Math.toIntExact(temp.get(1).getTeam().getId());
            visited[l][r] +=1;
        });
        boolean flag = true;
        for(int i =0 ;i<100;i++){
            for(int k =0;k<100;k++){
               if(!(visited[i][k] == 0 || visited[i][k] == 3))flag = false;
            }
        }
        Assertions.assertThat(flag).isTrue();

    }

}