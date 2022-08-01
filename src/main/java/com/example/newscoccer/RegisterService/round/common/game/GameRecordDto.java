package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 팀 , 선수들의 기록을 받아옴
 */
@Data
public class GameRecordDto {

    private Long roundId;
    private List<GameResultTeamDto> teams = new ArrayList<>(); // 팀A vs 팀
    private List<GameResultPlayerDto> players = new ArrayList<>();

}
