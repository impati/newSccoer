package com.example.newscoccer.SearchService.player.search;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Data
@AllArgsConstructor
public class PlayerSearchResponse extends DataTransferObject {
    private Long playerId;
    private String playerName;
    private String teamName;
    private Position position;
}
