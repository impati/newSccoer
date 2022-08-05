package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                leagueGame(round);
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
        LeagueRound leagueRound = (LeagueRound)round;
        // 시즌 리그의 팀기록을 모두 가져온 후 승점과 , 득실차를 계산
        Map<Long , TeamRankInfo> result = new HashMap<>();
        List<TeamLeagueRecord> findFirstTeamLeagueRecord = new ArrayList<>();
        List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository.findByLeagueAndSeasonForRank(leagueRound.getLeague(),leagueRound.getSeason());
        teamLeagueRecordList.stream()
                .forEach(ele->{
                        Long teamId = ele.getTeam().getId();
                        if(result.containsKey(teamId)){
                            TeamRankInfo rankInfo = result.get(teamId);
                            rankInfo.Calc(ele.getMatchResult(),ele.getScore(),ele.getOppositeScore());
                        }
                        else{
                            findFirstTeamLeagueRecord.add(ele);
                            TeamRankInfo rankInfo = new TeamRankInfo();
                            rankInfo.Calc(ele.getMatchResult(),ele.getScore(),ele.getOppositeScore());
                            result.put(teamId,rankInfo);
                        }
                });
        // 계산된 결과를 바탕으로 순위를 결정지음.
        for(var record1 :result.keySet()){
            int r = 1;
            TeamRankInfo cur = result.get(record1);
            for(var record2 : result.keySet()){
                TeamRankInfo nxt = result.get(record2);
                if(cur.getPoint() < nxt.getPoint()) r++;
                else if(cur.getPoint() == nxt.getPoint() && cur.getDiff() < nxt.getDiff()) r++;
            }
            cur.setRank(r);
        }



        //가장 최근의 팀 경기에 순위를 세팅
        findFirstTeamLeagueRecord
                .stream()
                .forEach(ele->{
                    Long teamId = ele.getTeam().getId();
                    int r = result.get(teamId).getRank();
                    ele.setRank(r);
                });

    }
    @Data
    @ToString
    static class TeamRankInfo{
        private int point; // 승점
        private int diff; // 득실차
        private int rank;
        public void Calc(MatchResult matchResult , int score,int opposite){
            if(matchResult.equals(MatchResult.WIN)) this.point += 3;
            else if(matchResult.equals(MatchResult.DRAW)) this.point +=1;
            diff += score - opposite;
        }
    }





}
