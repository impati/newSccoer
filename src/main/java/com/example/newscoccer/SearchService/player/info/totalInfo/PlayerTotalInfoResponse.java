package com.example.newscoccer.SearchService.player.info.totalInfo;

import com.example.newscoccer.SearchService.common.RankInfo;
import lombok.Data;

@Data
public class PlayerTotalInfoResponse extends RankInfo {

    private int goal;
    private int assist;
    private int pass;
    private int shooting;
    private int validShooting;
    private int foul;
    private int defense;

    private int win;
    private int draw;
    private int lose;

    private int isBest;
    private double avgGrade;
    private int gameNumber;

}
