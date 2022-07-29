package com.example.newscoccer.SearchService.team.info.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamLeagueInfoResponse extends DataTransferObject {
    private int rank;
    private int gameNumber;

    private int point;
    private int win;
    private int draw;
    private int lose;

    private int gain;
    private int lost;
    private int diff;

    private List<ParticipatePlayer> participatePlayers = new ArrayList<>();


}

