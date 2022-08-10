package com.example.newscoccer.SearchService.record.player;

import com.example.newscoccer.domain.Direction;
import com.example.newscoccer.domain.SortType;

/**
 *  선수 순위 조회 여러 정렬 조건이 존재할 수 있음
 *  리그 -> 리그 단위로 조회
 *  챔피언스 -> 시즌 단위로 조회
 */
public interface SearchPlayerRecord {

    SearchPlayerRecordResponse searchLeaguePlayerRecord(Long leagueId , int season, SortType sortType , Direction direction);
    SearchPlayerRecordResponse searchChampionsPlayerRecord(int season, SortType sortType , Direction direction );

}
