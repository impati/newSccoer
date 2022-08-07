package com.example.newscoccer.domain.record;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 레코드 리스트 에서 win , draw , lose 결과를 계산해서 넘겨줌
 */
@Getter
@Slf4j
public class MatchResultUtils  {
    private int win ;
    private int draw ;
    private int lose ;

    public MatchResultUtils(List records) {
        for (Object record : records) {
            if(record instanceof TeamRecord ) {
                TeamRecord teamRecord = (TeamRecord) record;
                if (teamRecord.getMatchResult() == MatchResult.WIN) {
                    win += 1;
                } else if (teamRecord.getMatchResult() == MatchResult.DRAW) {
                    draw += 1;
                } else {
                    lose += 1;
                }
            }
            else{
                PlayerRecord playerRecord = (PlayerRecord) record;
                if (playerRecord.getMatchResult() == MatchResult.WIN) {
                    win += 1;
                } else if (playerRecord.getMatchResult() == MatchResult.DRAW) {
                    draw += 1;
                } else {
                    lose += 1;
                }
            }
        }
    }

    // 입력으로 들어오는 matchResult의 대응하는 결과를 리턴함
    public static MatchResult oppositeMatchResult(MatchResult matchResult){
        if(matchResult== MatchResult.WIN) return matchResult = MatchResult.LOSE;
        else if(matchResult == MatchResult.DRAW) return matchResult = MatchResult.DRAW;
        else return matchResult = MatchResult.WIN;
    }


}
