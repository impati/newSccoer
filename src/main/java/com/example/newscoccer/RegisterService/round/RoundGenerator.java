package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.Round.Round;

public interface RoundGenerator {
    /**
     * @Param round
     * 시즌 정보 , 라운드 정보를 받아 .
     * 라운드 정보를 통해 기록을 생성해줌.
     *
     * 라운드
     * 1. 리그의 경우 -> 항상 4대 리그를 시즌 단위로 한번에 생성 -> teamLeagueRecord, playerLeagueRecord
     * 2. 챔피언스 경우 -> 4대리그의 전 시즌 Top4 (없을 경우 default 작동.) -> teamChampionsRecord, playerChampionsRecord
     *
     */
    void generator(Round round);
}


