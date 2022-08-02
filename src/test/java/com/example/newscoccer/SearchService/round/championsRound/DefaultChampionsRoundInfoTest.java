package com.example.newscoccer.SearchService.round.championsRound;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultChampionsRoundInfoTest {

    @Autowired
    ChampionsRoundInfo championsRoundInfo;

    @Test
    @DisplayName("시즌 + 라운드로 챔피언스기록 조회하기 16강")
    public void championsRoundInfo() throws Exception{
        // given
        //있는 정보를 조회
        // when
        ChampionsRoundInfoResponse resp = championsRoundInfo.championsRoundInformation(new ChampionsRoundInfoRequest(0, 16));
        // then
        Assertions.assertThat(resp.getFirstRound().size()).isEqualTo(8);
        Assertions.assertThat(resp.getSecondRound().size()).isEqualTo(8);


        resp.getFirstRound().stream().forEach(ele->{
            System.out.println(" 1st = teamA :  Team B"  + ele.getTeamA().getName() + " " + ele.getTeamB().getName());
        });

        resp.getSecondRound().stream().forEach(ele->{
            System.out.println("2st = teamA :  Team B"  + ele.getTeamA().getName() + " " + ele.getTeamB().getName());
        });






    }


}
