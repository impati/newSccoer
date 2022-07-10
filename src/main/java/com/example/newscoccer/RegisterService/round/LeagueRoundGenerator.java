package com.example.newscoccer.RegisterService.round;


public interface LeagueRoundGenerator {
    /**
     * @param season
     * 라운드
     *  항상 4대 리그를 시즌 단위로 한번에 생성 -> teamLeagueRecord, playerLeagueRecord
    */
    void generator(int season);
}
