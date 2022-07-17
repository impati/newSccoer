package com.example.newscoccer.SearchService.director.league;

import com.example.newscoccer.SearchService.common.EntityLeagueInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 기록 save 기능 추가시 다시 테스트.
 */
@SpringBootTest
class DefaultDirectorLeagueInfoTest {


    @Autowired
    EntityLeagueInfo<DirectorLeagueInfoRequest , DirectorLeagueInfoResponse> leagueInfo ;


    @Test
    @DisplayName("감독 리그 정보 가져오기")
    void directorLeagueInfoTest(){
        DirectorLeagueInfoResponse response = leagueInfo.leagueInfo(new DirectorLeagueInfoRequest(1L, 0));
        int win = response.getWin();
        int draw = response.getDraw();
        int lose = response.getLose();
        int rank = response.getRank();
        System.out.println("rank = " + rank  + "win = " + win + "draw = " + draw + "lose = " + lose);
    }

}