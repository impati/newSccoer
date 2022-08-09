package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.MatchResultUtils;


public class TotalComparisonRecordResponse extends DataTransferObject {
    private String teamAName;

    private int winA;
    private int drawA;
    private int loseA;

    private double avgGoalA;
    private double avgGoalB;


    private int sumGoalA;
    private int sumGoalB;
    private int sz = 0;



    private int winB;
    private int drawB;
    private int loseB;



    private String teamBName;


    public TotalComparisonRecordResponse(String teamAName, String teamBName) {
        this.teamAName = teamAName;
        this.teamBName = teamBName;
    }

    /**
     * teamAUpdate teamBUpdate 같이 호출
     * @param matchResult
     * @param goal
     */
    public void update(MatchResult matchResult ,int goal,int oppositeGoal){
        if(matchResult == MatchResult.WIN) winA +=1;
        if(matchResult == MatchResult.DRAW) drawA += 1;
        if(matchResult == MatchResult.LOSE) loseA +=1;

        MatchResult oppositeMatchResult = MatchResultUtils.oppositeMatchResult(matchResult);
        if(oppositeMatchResult == MatchResult.WIN) winB +=1;
        if(oppositeMatchResult == MatchResult.DRAW) drawB += 1;
        if(oppositeMatchResult == MatchResult.LOSE) loseB +=1;

        sumGoalA += goal;
        sumGoalB += oppositeGoal;

        sz ++;
        avgCalc();
    }

    private void avgCalc(){
        avgGoalA = Math.round((sumGoalA / (double)sz) * 100 ) / 100.0;
        avgGoalB = Math.round((sumGoalB / (double)sz) * 100 ) / 100.0;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public int getWinA() {
        return winA;
    }

    public int getDrawA() {
        return drawA;
    }

    public int getLoseA() {
        return loseA;
    }

    public double getAvgGoalA() {
        return avgGoalA;
    }

    public double getAvgGoalB() {
        return avgGoalB;
    }

    public int getWinB() {
        return winB;
    }

    public int getDrawB() {
        return drawB;
    }

    public int getLoseB() {
        return loseB;
    }

    public String getTeamBName() {
        return teamBName;
    }


}
