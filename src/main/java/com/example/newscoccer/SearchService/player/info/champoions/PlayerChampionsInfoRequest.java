package com.example.newscoccer.SearchService.player.info.champoions;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerChampionsInfoRequest extends DataTransferObject {
    private Long playerId;
    private int season;
}
