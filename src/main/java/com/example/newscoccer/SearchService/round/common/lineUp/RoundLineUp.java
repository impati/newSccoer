package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.Round.RoundFunction;

public interface RoundLineUp extends RoundFunction {
    RoundLineUpResponse lineUp(RoundLineUpRequest req);
}
