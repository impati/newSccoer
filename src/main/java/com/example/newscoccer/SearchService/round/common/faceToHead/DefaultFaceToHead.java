package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultFaceToHead implements FaceToHead{


    private final RoundRepository roundRepository;

    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    /**
     * 리그인 경우  시즌의 정보를 종합해서 리턴
     *
     * 챔피언스 경우  시즌 정보와 전체 시즌 정보를 종합해서 리턴
     */
    @Override
    public ComparisonRecordResponse comparison(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);
        RoundFeature<ComparisonRecordResponse> feature = new RoundFeature<>() {
            @Override
            public ComparisonRecordResponse leagueSolved() {
                ComparisonRecordResponse resp = new ComparisonRecordResponse();





                return null;
            }

            @Override
            public ComparisonRecordResponse championsSolved() {
                return null;
            }
        };

        return new RoundTemplate().action(round,feature);
    }

    @Override
    public RecentRecordResponse recentRecord(Long roundId) {
        return null;
    }

    @Override
    public TopPlayerResponse top5Player(Long roundId) {
        return null;
    }

    @Override
    public TotalComparisonRecordResponse totalComparison(Long roundId) {
        return null;
    }


}
