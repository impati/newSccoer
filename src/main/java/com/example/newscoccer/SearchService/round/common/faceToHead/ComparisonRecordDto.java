package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.record.MatchResult;

import java.util.LinkedList;
import java.util.Queue;


public class ComparisonRecordDto {

    private String teamName;
    // 리그 : 시즌 성적
    // 챔피언스 , 유로파 :전체 성적
    private int win;
    private int draw;
    private int lose;
    // toString of win + draw + lose
    private String total;
    // 리그 : 5 최근 경기
    // 챔피언스 :시즌 경기

    // toString of recentWin...
    private String recent;
    private int recentWin;
    private int recentDraw;
    private int recentLose;

    //평균 득점
    private double avgScore;
    //평균 실점
    private double avgLost;

    public ComparisonRecordDto(String teamName) {
        this.teamName = teamName;
    }

    private int sumScore = 0;
    private int sumLost = 0;
    private Queue<MatchResult> recentResult = new LinkedList<>();
    private int sz = 0;


    // 챔피언스 전체 기록
    public void championsTotalUpdate(MatchResult matchResult , int score,int lost){
        if(matchResult == MatchResult.WIN) win += 1;
        else if(matchResult == MatchResult.DRAW) draw +=1;
        else lose +=1;
        avgCalc(score,lost);
    }
    // 챔피언스 시즌기록(승,무,패)
    public void championsSeasonUpdate(MatchResult matchResult){
        if(matchResult == MatchResult.WIN) recentWin += 1;
        else if(matchResult == MatchResult.DRAW) recentDraw +=1;
        else recentLose +=1;
    }
    public void leagueUpdate(MatchResult matchResult , int score,int lost){
        if(recentResult.size() == 5) {
            MatchResult oldResult = recentResult.poll();
            if(oldResult == MatchResult.WIN) recentWin -=1;
            else if(oldResult == MatchResult.DRAW) recentDraw -=1;
            else recentLose -=1;
        }
        recentResult.add(matchResult);

        if(matchResult == MatchResult.WIN) {
            recentWin += 1;
            win += 1;
        }
        else if(matchResult == MatchResult.DRAW) {
            recentDraw +=1;
            draw += 1;
        }
        else {
            recentLose +=1;
            lose += 1;
        }

        avgCalc(score,lost);
    }

    private void avgCalc(int score , int lost){
        // 평균 처리
        sumScore += score ;
        sumLost += lost;
        sz += 1;
        avgScore = Math.round((sumScore / sz) * 100) / 100.0;
        avgLost =  Math.round((sumLost / sz) * 100) / 100.0;

    }













    public String getTotal(){
        return win + " 승 " + draw  + " 무 " + lose + " 패 ";
    }

    public String getRecent(){
        return recentWin + " 승 " + recentDraw  + " 무 " + recentLose + " 패 ";
    }






    public String getTeamName() {
        return teamName;
    }

    public int getWin() {
        return win;
    }

    public int getDraw() {
        return draw;
    }

    public int getLose() {
        return lose;
    }

    public int getRecentWin() {
        return recentWin;
    }

    public int getRecentDraw() {
        return recentDraw;
    }

    public int getRecentLose() {
        return recentLose;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public double getAvgLost() {
        return avgLost;
    }
}
