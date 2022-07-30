package com.example.newscoccer.SearchService.round.leagueRound;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundInfoRequest extends DataTransferObject {
    private Long leagueId;
    private int season;
    private int roundSt;
}
