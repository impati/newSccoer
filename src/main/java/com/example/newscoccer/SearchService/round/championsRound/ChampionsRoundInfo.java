package com.example.newscoccer.SearchService.round.championsRound;

/**
 * 시즌 + 라운드로 챔피언스 전적 정보를 조회
 */
public interface ChampionsRoundInfo {
    ChampionsRoundInfoResponse championsRoundInformation(ChampionsRoundInfoRequest req);
}
