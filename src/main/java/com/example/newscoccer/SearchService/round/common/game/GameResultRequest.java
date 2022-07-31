package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResultRequest extends DataTransferObject {
    private Long roundId;
}
