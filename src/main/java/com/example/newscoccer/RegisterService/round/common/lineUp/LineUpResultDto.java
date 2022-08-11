package com.example.newscoccer.RegisterService.round.common.lineUp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * participatePlayer 에 참가한 선수,참가한 포지션을 들을 모조리 넣어줌.
 *
 * */
@Data
public class LineUpResultDto {
    private Long roundId;
    private List<LineUpResult> participatePlayer = new ArrayList<>();
}
