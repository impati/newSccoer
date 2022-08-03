package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  경기가 끝날 때 마다 호출함으로써
 *  경기가 끝난 후 취해야할 액션을 정의
 *
 *  리그 ->
 *      1.리그 랭크를 업데이트
 *      2.전체 리그가 현재 라운드를 마무리 했을시 라운드 업데이트
 *      3.젠처 리그가 마지막 라운드를 마무리 했을 시 시즌 업데이트 준비 1
 *  챔피언스리그 ->
 *      1.현재 라운드 모든 1, 2차 게임이 끝나면 새로운 라운드를 생성.
 *      2.결승까지 끝났을 시에는 시즌 업데이트 준비 2
 *
 *  ...
 *  ...
 *
 *  모두 시즌 업데이트 준비가 완료됬을 시 시즌이 넘어갑니다.->새로운 시즌
 */
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AfterGameDone implements GameDoneTroubleShooter{
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Override
    public void AfterGameDone(Round round) {
        RoundFeature<Void> feature = new RoundFeature<Void>() {
            @Override
            public Void leagueSolved() {
                return null;
            }

            @Override
            public Void championsSolved() {
                return null;
            }
        };
        new RoundTemplate().action(round,feature);
    }


    private void leagueGame(Round round){

    }
}
