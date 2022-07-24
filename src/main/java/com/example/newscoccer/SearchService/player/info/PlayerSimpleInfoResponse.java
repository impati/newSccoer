package com.example.newscoccer.SearchService.player.info;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerSimpleInfoResponse extends DataTransferObject {
    private String playerName;
    private String teamName;
    private Position position;
    private double currentRating;
}
