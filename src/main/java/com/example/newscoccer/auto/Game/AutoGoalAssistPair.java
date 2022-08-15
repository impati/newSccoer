package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutoGoalAssistPair {
    private Long shooter;
    private Long assistant;
    private GoalType goalType;

}
