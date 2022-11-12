package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.Round.RoundFunction;

public interface LineUpRegister extends RoundFunction {
    void lineUpRegister(LineUpResultDto lineUpResultDto);
}
