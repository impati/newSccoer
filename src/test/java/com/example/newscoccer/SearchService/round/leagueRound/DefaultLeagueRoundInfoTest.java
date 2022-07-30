package com.example.newscoccer.SearchService.round.leagueRound;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultLeagueRoundInfoTest {

    @Autowired
    LeagueRoundInfo leagueRoundInfo;

    @Test
    @DisplayName("리그 라운드 정보를 가져와보자")
    public void leagueRoundInfo() throws Exception{
        // given

        // when
        LeagueRoundInfoResponse resp = leagueRoundInfo.leagueRoundInformation(new LeagueRoundInfoRequest(1L,0 ,1));
        // then
        Assertions.assertThat(resp.getRoundInfoDtoList().size()).isEqualTo(8);
        for(var ret : resp.getRoundInfoDtoList()){
            System.out.println("teamA : " + ret.getTeamA().getName() + "teamB :" + ret.getTeamB().getName());
            System.out.println("teamA Score: " + ret.getScoreA() + "teamB Score:" + ret.getScoreB());
            System.out.println("Done ?  : " + (ret.isDone()));

            System.out.println("+========================+");
        }

    }
}