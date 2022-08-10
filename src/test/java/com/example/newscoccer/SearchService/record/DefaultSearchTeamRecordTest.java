package com.example.newscoccer.SearchService.record;

import com.example.newscoccer.SearchService.record.team.SearchTeamRecord;
import com.example.newscoccer.SearchService.record.team.SearchTeamRecordResponse;
import com.example.newscoccer.SearchService.record.team.TeamRecordDto;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class DefaultSearchTeamRecordTest {




    @Autowired
    SearchTeamRecord searchTeamRecord;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;

    @Test
    @DisplayName("리그 -  팀 시즌 순위 조회")
    public void teamLeagueRecord() throws Exception{
        // given

        League league = new League("testLeague");
        leagueRepository.save(league);


        List<Team> teamList = new ArrayList<>();
        for(int i = 1;i<=16;i++){
            Team team = Team.createTeam(league,"testTeam" + i);
            teamRepository.save(team);

            teamList.add(team);
            for(int k = 0;k<20;k++){
                Player player = Player.createPlayer("testPlayer" + i, Position.ST,team,new Stat());
                playerRepository.save(player);
            }

        }
        for(int i =  1;i<=10;i++){
            for(var t : teamList){
                LeagueRound leagueRound = new LeagueRound(league,1000,i);
                leagueRound.setRoundStatus(RoundStatus.DONE);
                roundRepository.save(leagueRound);
                TeamLeagueRecord tlr = TeamLeagueRecord.create(leagueRound,t);
                tlr.setScore(RandomNumber.returnRandomNumber(0,5));
                tlr.setRank(RandomNumber.returnRandomNumber(1,16));
                teamLeagueRecordRepository.save(tlr);
            }
        }

        // when
        SearchTeamRecordResponse resp = searchTeamRecord.searchLeagueTeamRecord(league.getId(), 1000);
        // then

        List<TeamRecordDto> resultList = resp.getResultList();
        for(int i = 0 ; i<resultList.size();i++){
            for(int k = i ; k < resultList.size();k++){
                Assertions.assertThat(resultList.get(i).getRank() <= resultList.get(k).getRank()).isTrue();
            }
        }

    }



    @Test
    @DisplayName("챔피언스 -  팀 시즌 순위 조회")
    public void champions() throws Exception{
        // given
        League league1 = new League("league1");
        League league2 = new League("league2");
        leagueRepository.save(league1);
        leagueRepository.save(league2);

        Team team11 = Team.createTeam(league1,"team11");
        Team team12 = Team.createTeam(league1,"team12");

        Team team21 = Team.createTeam(league2,"team21");
        Team team22 = Team.createTeam(league2,"team22");

        teamRepository.save(team11);
        teamRepository.save(team12);
        teamRepository.save(team21);
        teamRepository.save(team22);

        // when

        saveChampionsRecord(1000,16,1,team11, team21);
        saveChampionsRecord(1000,16,2,team11, team21);

        saveChampionsRecord(1000,16,1,team12, team22);
        saveChampionsRecord(1000,16,2,team12, team22);

        saveChampionsRecord(1000,8,1,team11,team22);
        saveChampionsRecord(1000,8,2,team11,team22);


        SearchTeamRecordResponse resp = searchTeamRecord.searchChampionsTeamRecord(1000);

        // then

        Assertions.assertThat(resp.getResultList().size()).isEqualTo(4);
        resp.getResultList().stream().forEach(ele->{
            System.out.println("TeamName = " + ele.getTeamName()  +    "rank ="  + ele.getRank());
        });

    }

    private void saveChampionsRecord(int season , int roundSt ,int fs , Team team11, Team team21) {
        ChampionsRound round = new ChampionsRound(season,roundSt,1);
        round.setRoundStatus(RoundStatus.DONE);
        roundRepository.save(round);

        TeamChampionsRecord tcr1 = TeamChampionsRecord.create(round, team11,fs);
        TeamChampionsRecord tcr2 = TeamChampionsRecord.create(round, team21,fs);

        //  경기 기록
        int scoreA = RandomNumber.returnRandomNumber(0,5);
        int scoreB = RandomNumber.returnRandomNumber(0,5);
        tcr1.setScore(scoreA);
        tcr1.setOppositeScore(scoreA);

        tcr2.setOppositeScore(scoreB);
        tcr2.setScore(scoreB);

        if(scoreA > scoreB) {
            tcr1.setMatchResult(MatchResult.WIN);
            tcr2.setMatchResult(MatchResult.LOSE);
        }
        else if(scoreA < scoreB){
            tcr1.setMatchResult(MatchResult.LOSE);
            tcr2.setMatchResult(MatchResult.WIN);
        }
        else {
            tcr1.setMatchResult(MatchResult.DRAW);
            tcr2.setMatchResult(MatchResult.DRAW);
        }

        tcr1.setRank(roundSt);
        tcr2.setRank(roundSt);
        // 저장
        teamChampionsRecordRepository.save(tcr1);
        teamChampionsRecordRepository.save(tcr2);
    }


}