package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TeamA < TeamB  (team ID)
 */
@Data
public class GameResultResponse extends DataTransferObject {
    private Boolean isDone ;
    private GameResultTeamDto teamA;
    private GameResultTeamDto teamB;

    private List<GameResultPlayerDto> playerADtoList = new ArrayList<>();
    private List<GameResultPlayerDto> playerBDtoList = new ArrayList<>();

}
