package com.example.newscoccer.SearchService.player.info.totalInfo;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerTotalInfoRequest extends DataTransferObject {
    private Long playerId;
}
