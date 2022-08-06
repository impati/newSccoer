package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Season;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class AfterGameDoneTest {




    @Autowired
    AfterGameDone afterGameDone;

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;


    @Autowired
    SeasonRepository seasonRepository;
    @Test
    @DisplayName("경기 후 순위 테스트")
    public void rankFeature() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        List<Team> teamAList = new ArrayList<>();
        List<Team> teamBList = new ArrayList<>();

        MakeTeam("A", league, teamAList);
        MakeTeam("B", league, teamBList);


        List<Round> roundList = new ArrayList<>();
        for(int i  =  1; i<= 30;i++){
            for(int k = 0 ; k< 11;k++){
                LeagueRound leagueRound = new LeagueRound(league,0,i);
                roundList.add(leagueRound);
                roundRepository.save(leagueRound);

                leagueRound.setRoundStatus(RoundStatus.DONE);
                TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound,teamAList.get(k));
                TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound,teamBList.get(k));



                int rn1 = RandomNumber.returnRandomNumber(0,5);
                int rn2 = RandomNumber.returnRandomNumber(0,5);

                tlrA.setScore(rn1);
                tlrA.setOppositeScore(rn2);

                tlrB.setScore(rn2);
                tlrB.setOppositeScore(rn1);


                if(rn1 > rn2) {
                    tlrA.setMatchResult(MatchResult.WIN);
                    tlrB.setMatchResult(MatchResult.LOSE);
                }
                else if(rn1 == rn2){
                    tlrA.setMatchResult(MatchResult.DRAW);
                    tlrB.setMatchResult(MatchResult.DRAW);
                }
                else{
                    tlrA.setMatchResult(MatchResult.LOSE);
                    tlrB.setMatchResult(MatchResult.WIN);
                }
                teamLeagueRecordRepository.save(tlrA);
                teamLeagueRecordRepository.save(tlrB);


            }
        }
        // when

        // then

        for (var element : roundList){
            afterGameDone.AfterGameDone(element);
            Season season = seasonRepository.findById(1L).orElse(null);
            System.out.println("season : " + SeasonUtils.currentSeason  + " roundSt : " + SeasonUtils.currentLeagueRoundSt);
            System.out.println("season : " + season.getCurrentSeason() + " roundSt : " + season.getCurrentLeagueRoundSt());
        }

    }
    private void MakeTeam(String del , League league, List<Team> teamBList) {
        for(int i = 1;i<=11;i++){
            Team team = Team.createTeam(league,"testTeam" + del  + i);
            teamRepository.save(team);
            teamBList.add(team);
        }
    }











}