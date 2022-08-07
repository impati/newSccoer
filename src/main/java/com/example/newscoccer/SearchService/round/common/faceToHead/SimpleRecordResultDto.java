package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.record.MatchResult;
import lombok.Data;


/**
 *  팀 A       팀 B
 *  score     score
 *  결과        결과
 */
@Data
public class SimpleRecordResultDto {
    private String teamAName;
    private int scoreA;
    private MatchResult matchResultA;

    private String teamBName;
    private int scoreB;
    private MatchResult matchResultB;


    public void updateTeamA(String name,int score,MatchResult matchResult){
        this.teamAName = name;
        this.scoreA = score;
        this.matchResultA = matchResult;
    }
    public void updateTeamB(String name,int score,MatchResult matchResult){
        this.teamBName = name;
        this.scoreB = score;
        this.matchResultB = matchResult;
    }
    public String getStringOfSimPleRecordResult() {
        String str = this.teamAName;
        str += " ";
        str += this.scoreA;
        str += " ";
        str += matchResultTo(this.matchResultA);
        str += teamBName;
        str += " ";
        str += this.scoreB;
        str += " ";
        str += matchResultTo(this.matchResultB);
        return str;
    }



    private String matchResultTo(MatchResult matchResult){
        if(matchResult == MatchResult.WIN) return "승";
        else if(matchResult == MatchResult.DRAW) return "무";
        else return "패";
    }

}
