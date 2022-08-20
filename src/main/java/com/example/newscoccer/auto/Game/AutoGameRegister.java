package com.example.newscoccer.auto.Game;

import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPairDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordDto;

import java.util.List;

/**
 * 자동 게임 저장 기능 , 골 - 어시 페어 저장 기능
 *
 */
public interface AutoGameRegister {
    void autoGameRegister(Long roundId);
    void autoGameRegisterForTest(Long roundId, GameRecordDto ret , List<GoalAssistPairDto> nxt);
}
