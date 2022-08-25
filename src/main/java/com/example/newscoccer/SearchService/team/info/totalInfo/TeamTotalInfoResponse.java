package com.example.newscoccer.SearchService.team.info.totalInfo;

import com.example.newscoccer.SearchService.common.RankInfo;
import lombok.Data;

@Data
public class TeamTotalInfoResponse extends RankInfo {

    private int win;
    private int draw;
    private int lose;

    private int gain;
    private int lost;
    private int diff;



}
