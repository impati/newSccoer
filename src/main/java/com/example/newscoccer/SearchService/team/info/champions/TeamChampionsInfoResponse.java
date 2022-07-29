package com.example.newscoccer.SearchService.team.info.champions;

import com.example.newscoccer.SearchService.team.ParticipatePlayer;
import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamChampionsInfoResponse extends DataTransferObject {
    private int rank;


    private int win;
    private int draw;
    private int lose;

    private int gain;
    private int lost;
    private int diff;

    List<ParticipatePlayer> participatePlayers = new ArrayList<>();
}
