package com.example.newscoccer.SearchService.record;

/**
 * 시즌 ,리그 ,으로 팀 기록 , 순위 조회
 */
public interface SearchTeamRecord {
    SearchTeamRecordResponse searchLeagueTeamRecord(Long leagueId, int season);
    SearchTeamRecordResponse searchChampionsTeamRecord(int season);
}
