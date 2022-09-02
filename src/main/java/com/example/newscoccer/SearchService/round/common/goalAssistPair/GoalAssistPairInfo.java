package com.example.newscoccer.SearchService.round.common.goalAssistPair;

import com.example.newscoccer.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalAssistPairInfo {
    private String goalPlayerName;
    private String assistPlayerName;
    private GoalType goalType;

}
