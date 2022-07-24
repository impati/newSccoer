package com.example.newscoccer.SearchService.player.info;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Data
@AllArgsConstructor
public class PlayerSimpleInfoRequest extends DataTransferObject {
    private Long playerId;
}
