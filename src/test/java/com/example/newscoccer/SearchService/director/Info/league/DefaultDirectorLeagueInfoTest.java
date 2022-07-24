package com.example.newscoccer.SearchService.director.Info.league;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.director.Info.league.DirectorLeagueInfoRequest;
import com.example.newscoccer.SearchService.director.Info.league.DirectorLeagueInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO : 기록 save 기능 추가시 다시 테스트.
 */
@SpringBootTest
class DefaultDirectorLeagueInfoTest {


    @Autowired
    EntityRecordInfo<DirectorLeagueInfoRequest, DirectorLeagueInfoResponse> leagueInfo ;


    @Test
    @DisplayName("감독 리그 정보 가져오기")
    void directorLeagueInfoTest(){


        DirectorLeagueInfoResponse response = leagueInfo.recordInfo(new DirectorLeagueInfoRequest(1L, 0));
        int win = response.getWin();
        int draw = response.getDraw();
        int lose = response.getLose();
        int rank = response.getRank();
        System.out.println("rank = " + rank  + "win = " + win + "draw = " + draw + "lose = " + lose);
    }

}