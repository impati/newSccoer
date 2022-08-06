package com.example.newscoccer.SearchService.round.common.faceToHead;

import lombok.Data;

@Data
public class ComparisonRecord {

    private String teamName;
    // 리그 : 시즌 성적
    // 챔피언스 , 유로파 :전체 성적
    private int win;
    private int draw;
    private int lose;
    // 리그 : 5 최근 경기
    // 챔피언스 :시즌 경기
    private int recentWin;
    private int recentDraw;
    private int recentLose;

    //평균 득점
    private int avgScore;
    //평균 실점
    private int avgLost;

}
