package com.example.newscoccer.SearchService.round.leagueRound;

/**
 *  리그별 , 시즌 , 라운드 별
 *  팀 상대 정보를 가져옴.
 */
public interface LeagueRoundInfo {
    LeagueRoundInfoResponse leagueRoundInformation(LeagueRoundInfoRequest req);
}
