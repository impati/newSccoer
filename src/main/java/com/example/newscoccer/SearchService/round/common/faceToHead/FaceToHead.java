package com.example.newscoccer.SearchService.round.common.faceToHead;

/**
 * 두 팀간 맞대결 정보
 * 리그
 *      1.리그 전적 비교
 *      2.최근 리그 양팀 맞대결
 *      3.탑플레이어
 *
 * 챔피언스리그
 *      1.챔피언스 전적 비교 (전체 , 현재)
 *      2.최근 챔피언스 양팀 맞대결
 *      3.탑플레이어
 *
 * 공통
 * 양팀 총 상대 전적.
 *
 *
 */
public interface FaceToHead {
    ComparisonRecordResponse comparison(Long roundId);
    RecentRecordResponse recentRecord(Long roundId);
    TopPlayerResponse top5Player(Long roundId);
    TotalComparisonRecordResponse totalComparison(Long roundId);
}
