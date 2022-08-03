package com.example.newscoccer.RegisterService.round.common.GoalAssistPair;

import com.example.newscoccer.domain.Round.Round;

import java.util.List;

public interface GoalAssistPair {
    void goalPairRegister(List<GoalAssistPairDto> pairList, Round round);
}
