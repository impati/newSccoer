package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundLineUpDto extends DataTransferObject {
    private Long playerId;
    private String playerName;
    private Position position;
}
