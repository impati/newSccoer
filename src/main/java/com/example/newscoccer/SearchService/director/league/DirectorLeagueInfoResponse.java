package com.example.newscoccer.SearchService.director.league;

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
