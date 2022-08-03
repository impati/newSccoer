package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.Round.Round;

/**
 *  경기가 끝난 후 GoalAssistPair 에서 호출.
 *  경기가 끝난 후 처리. or 취해야할 액션
 *
 *  리그 ->
 *  침패언스->
 *  유로파->
 */
public interface GameDoneTroubleShooter {
    void AfterGameDone(Round round);
}
