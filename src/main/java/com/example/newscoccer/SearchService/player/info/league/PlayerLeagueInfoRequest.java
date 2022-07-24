package com.example.newscoccer.SearchService.player.info.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerLeagueInfoRequest extends DataTransferObject {
    private Long playerId;
    private int season;
}
