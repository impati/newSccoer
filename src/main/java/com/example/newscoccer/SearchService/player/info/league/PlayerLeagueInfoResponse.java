package com.example.newscoccer.SearchService.player.info.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLeagueInfoResponse extends DataTransferObject {
    private int rank;
    private double rating;

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
