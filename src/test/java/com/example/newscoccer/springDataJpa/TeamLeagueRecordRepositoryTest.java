package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
class TeamLeagueRecordRepositoryTest {

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    RoundRepository roundRepository;
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




    @Test
    @DisplayName("리그 + 시즌 + 라운드 정보로 라운드 정보가져오기")
    public void findLeagueRoundInfo() throws Exception{
        // given
        // when
        List<Round> roundList = roundRepository.findByLeagueRoundInfo(1L, 0, 1);
        List<TeamLeagueRecord> tlrList = teamLeagueRecordRepository.findLeagueRoundInfo(roundList);

        // then
        Assertions.assertThat(roundList.size()).isEqualTo(8);
        Assertions.assertThat(tlrList.size()).isEqualTo(16);
        Set<Long> r = new HashSet<>();

        for(var record : tlrList){
            r.add(record.getRound().getId());
        }
        Assertions.assertThat(r.size()).isEqualTo(8);



    }


    @Test
    @DisplayName("가장 최근의 팀 경기들 조회 ")
    public void findLastGame() throws Exception{
        // given

        // when

        // then

    }
}