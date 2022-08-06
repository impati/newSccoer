package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.record.MatchResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComparisonRecordDtoTest {


    @Test
    @DisplayName("comparisonRecord - league ver")
    public void comparisonRecordLeague() throws Exception{
        // given
        ComparisonRecordDto dto = new ComparisonRecordDto("");

        dto.leagueUpdate(MatchResult.LOSE,1,1);
        dto.leagueUpdate(MatchResult.LOSE,1,1);
        dto.leagueUpdate(MatchResult.LOSE,1,1);
        dto.leagueUpdate(MatchResult.LOSE,1,1);
        dto.leagueUpdate(MatchResult.LOSE,1,1);
        dto.leagueUpdate(MatchResult.WIN,1,1);

        dto.leagueUpdate(MatchResult.WIN,1,1);
        dto.leagueUpdate(MatchResult.WIN,1,1);
        dto.leagueUpdate(MatchResult.WIN,1,1);
        dto.leagueUpdate(MatchResult.WIN,1,1);
        dto.leagueUpdate(MatchResult.WIN,1,1);

        dto.leagueUpdate(MatchResult.DRAW,1,1);
        dto.leagueUpdate(MatchResult.DRAW,1,1);




        // then
        Assertions.assertThat(dto.getRecentWin()).isEqualTo(3);
        Assertions.assertThat(dto.getRecentDraw()).isEqualTo(2);
        Assertions.assertThat(dto.getRecentLose()).isEqualTo(0);
        Assertions.assertThat(dto.getWin()).isEqualTo(6);
        Assertions.assertThat(dto.getDraw()).isEqualTo(2);
        Assertions.assertThat(dto.getLose()).isEqualTo(5);
        Assertions.assertThat(dto.getAvgScore()).isEqualTo(1.0);
        Assertions.assertThat(dto.getAvgLost()).isEqualTo(1.0);

    }

}