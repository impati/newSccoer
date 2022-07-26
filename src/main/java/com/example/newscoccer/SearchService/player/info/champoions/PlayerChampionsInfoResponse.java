package com.example.newscoccer.SearchService.player.info.champoions;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

@Data
public class PlayerChampionsInfoResponse extends DataTransferObject {
    private int rank;

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
