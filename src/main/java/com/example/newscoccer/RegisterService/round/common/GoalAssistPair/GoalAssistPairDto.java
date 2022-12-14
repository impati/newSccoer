package com.example.newscoccer.RegisterService.round.common.GoalAssistPair;

import com.example.newscoccer.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalAssistPairDto {
    private Long goalPlayer;
    private Long assistPlayer;
    private GoalType goalType;
}
