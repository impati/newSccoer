package com.example.newscoccer.SearchService.director.Info.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DirectorLeagueInfoResponse extends DataTransferObject {
    private int rank;
    private int win;
    private int draw;
    private int lose;
}
