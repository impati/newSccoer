package com.example.newscoccer.SearchService.record.team;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamRecordDto {
    private int rank;
    private String teamName;
    private int gameNumber;

    private int win;
    private int draw;
    private int lose;
    private int point;
    private int gain;
    private int lost;
    private int diff;


    public TeamRecordDto(int rank, String teamName, int win, int draw, int lose, int gain, int lost) {
        this.rank = rank;
        this.teamName = teamName;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.gain = gain;
        this.lost = lost;
        this.setDiff(this.getGain() - this.getLost());
    }
}
