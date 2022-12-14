package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.*;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultFaceToHeadTest {

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    RoundRepository roundRepository;

    @Autowired
    FaceToHead faceToHead;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;

    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;

    @Autowired
    PlayerRepository playerRepository;



    @Test
    @DisplayName("전체 상대 전적")
    public void totalComparison() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        createRecord(league, teamA, teamB,1,5,2);
        createRecord(league, teamA, teamB,2,5,2);
        createRecord(league, teamA, teamB,3,5,2);
        createRecord(league,teamA,teamB,4,1,1);
        createRecord(league,teamA,teamB,5,2,5);
        createRecord(league,teamA,teamB,6,2,5);
        createRecord(league,teamA,teamB,7,2,5);
        createRecord(league,teamA,teamB,8,2,5);



        createChampionsRecord(teamA,teamB,1000,16,1,100,200);
        createChampionsRecord(teamA,teamB,1000,16,2,300,200);

        LeagueRound leagueRound = new LeagueRound(league,1001,1);
        roundRepository.save(leagueRound);

        TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound,teamA);
        TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound,teamB);
        teamLeagueRecordRepository.save(tlrA);
        teamLeagueRecordRepository.save(tlrB);

        // when

        TotalComparisonRecordResponse resp = faceToHead.totalComparison(leagueRound.getId());

        // then

        assertThat(resp.getWinA()).isEqualTo(4);
        assertThat(resp.getDrawA()).isEqualTo(1);
        assertThat(resp.getLoseA()).isEqualTo(5);
        assertThat(resp.getAvgGoalA()).isEqualTo(42.4);

        assertThat(resp.getAvgGoalB()).isEqualTo(42.7);
        assertThat(resp.getWinB()).isEqualTo(5);
        assertThat(resp.getDrawB()).isEqualTo(1);
        assertThat(resp.getLoseB()).isEqualTo(4);


    }
    @Test
    @DisplayName("챔피언스 탑 플레이어 ")
    public void championsTopPlayer() throws Exception{
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        List<Player> playerAList = new ArrayList<>();
        List<Player> playerBList = new ArrayList<>();

        for(int i =0;i<11;i++){
            Player playerA = Player.createPlayer("playerA " + i , Position.ST , teamA,new Stat());
            Player playerB = Player.createPlayer("playerB " + i , Position.GK , teamB,new Stat());

            playerAList.add(playerA);
            playerBList.add(playerB);


            playerA.setRating(RandomNumber.returnRandomNumber(0,2000));
            playerB.setRating(RandomNumber.returnRandomNumber(0,2000));
            playerRepository.save(playerA);
            playerRepository.save(playerB);
        }

        ChampionsRound round = new ChampionsRound(1000,1000,1);
        roundRepository.save(round);
        TeamChampionsRecord teamLeagueRecordA = TeamChampionsRecord.create(round,teamA,1);
        TeamChampionsRecord teamLeagueRecordB = TeamChampionsRecord.create(round,teamB,1);
        teamChampionsRecordRepository.save(teamLeagueRecordA);
        teamChampionsRecordRepository.save(teamLeagueRecordB);
        for(int k = 0;k<11;k++){
            PlayerChampionsRecord playerLeagueRecordA = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(playerAList.get(k),Position.ST,teamA,round);
            PlayerChampionsRecord playerLeagueRecordB = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(playerBList.get(k),Position.ST,teamB,round);

            playerLeagueRecordA.setGoal(RandomNumber.returnRandomNumber(1000,5000));
            playerLeagueRecordA.setAssist(RandomNumber.returnRandomNumber(1000,5000));

            playerLeagueRecordB.setGoal(RandomNumber.returnRandomNumber(1000,5000));
            playerLeagueRecordB.setAssist(RandomNumber.returnRandomNumber(1000,5000));


            playerChampionsRecordRepository.save(playerLeagueRecordA);
            playerChampionsRecordRepository.save(playerLeagueRecordB);
        }

        Long roundId = null;
        for(int i = 16 ;i >= 2 ; i/=2){
            for(int j = 1 ; j<=2;j++) {
                ChampionsRound championsRound = new ChampionsRound(1001, i, 1);
                roundRepository.save(championsRound);
                roundId = championsRound.getId();
                TeamChampionsRecord teamChampionsRecordA1 = TeamChampionsRecord.create(championsRound, teamA, j);
                TeamChampionsRecord teamChampionsRecordB1 = TeamChampionsRecord.create(championsRound, teamB, j);
                teamChampionsRecordRepository.save(teamChampionsRecordA1);
                teamChampionsRecordRepository.save(teamChampionsRecordB1);
                for (int k = 0; k < 11; k++) {
                    PlayerChampionsRecord playerLeagueRecordA = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(playerAList.get(k), Position.ST, teamA, championsRound);
                    PlayerChampionsRecord playerLeagueRecordB = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(playerBList.get(k), Position.ST, teamB, championsRound);

                    playerLeagueRecordA.setGoal(RandomNumber.returnRandomNumber(0, 5));
                    playerLeagueRecordA.setAssist(RandomNumber.returnRandomNumber(0, 5));

                    playerLeagueRecordB.setGoal(RandomNumber.returnRandomNumber(0, 5));
                    playerLeagueRecordB.setAssist(RandomNumber.returnRandomNumber(0, 5));
                    playerChampionsRecordRepository.save(playerLeagueRecordA);
                    playerChampionsRecordRepository.save(playerLeagueRecordB);
                }
            }
        }
        // when
        TopPlayerResponse resp = faceToHead.top5Player(roundId);
        // then

        for(var ele: resp.getTeamAPlayerList()){
            System.out.println(ele);
        }

        // then

        for(int i = 0;i<resp.getTeamAPlayerList().size();i++){
            for(int k = i + 1; k<resp.getTeamAPlayerList().size();k++){
                TopPlayerDto cur = resp.getTeamAPlayerList().get(i);
                TopPlayerDto nxt = resp.getTeamAPlayerList().get(k);
                if(cur.getGoal() + cur.getAssist() == nxt.getGoal() + nxt.getAssist()){
                    assertThat(cur.getRating() >= nxt.getRating()).isTrue();
                }
                else
                    assertThat(cur.getGoal() + cur.getAssist() > nxt.getGoal() + nxt.getAssist()).isTrue();

            }
        } for(int i = 0;i<resp.getTeamBPlayerList().size();i++){
            for(int k = i + 1; k<resp.getTeamBPlayerList().size();k++){
                TopPlayerDto cur = resp.getTeamBPlayerList().get(i);
                TopPlayerDto nxt = resp.getTeamBPlayerList().get(k);
                if(cur.getGoal() + cur.getAssist() == nxt.getGoal() + nxt.getAssist()){
                    assertThat(cur.getRating() >= nxt.getRating()).isTrue();
                }
                else
                    assertThat(cur.getGoal() + cur.getAssist() > nxt.getGoal() + nxt.getAssist()).isTrue();

            }
        }

    }
    @Test
    @DisplayName("리그 탑 플레이어")
    public void leagueTopPlayer() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"teamA");
        Team teamB = Team.createTeam(league,"teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        List<Player> playerAList = new ArrayList<>();
        List<Player> playerBList = new ArrayList<>();

        for(int i =0;i<11;i++){
            Player playerA = Player.createPlayer("playerA " + i , Position.ST , teamA,new Stat());
            Player playerB = Player.createPlayer("playerB " + i , Position.GK , teamB,new Stat());

            playerAList.add(playerA);
            playerBList.add(playerB);


            playerA.setRating(RandomNumber.returnRandomNumber(0,2000));
            playerB.setRating(RandomNumber.returnRandomNumber(0,2000));
            playerRepository.save(playerA);
            playerRepository.save(playerB);
        }
        Long roundId = null;
        for(int i = 1 ;i <= 5 ; i++){
            LeagueRound leagueRound = new LeagueRound(league,1000,i);
            roundRepository.save(leagueRound);
            roundId = leagueRound.getId();
            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);
            for(int k = 0;k<11;k++){
                PlayerLeagueRecord playerLeagueRecordA = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(playerAList.get(k),Position.ST,teamA,leagueRound);
                PlayerLeagueRecord playerLeagueRecordB = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(playerBList.get(k),Position.ST,teamB,leagueRound);

                playerLeagueRecordA.setGoal(RandomNumber.returnRandomNumber(0,5));
                playerLeagueRecordA.setAssist(RandomNumber.returnRandomNumber(0,5));

                playerLeagueRecordB.setGoal(RandomNumber.returnRandomNumber(0,5));
                playerLeagueRecordB.setAssist(RandomNumber.returnRandomNumber(0,5));
                playerLeagueRecordRepository.save(playerLeagueRecordA);
                playerLeagueRecordRepository.save(playerLeagueRecordB);
            }
        }



        // when

        TopPlayerResponse resp = faceToHead.top5Player(roundId);

        // then

        for(int i = 0;i<resp.getTeamAPlayerList().size();i++){
            for(int k = i + 1; k<resp.getTeamAPlayerList().size();k++){
                TopPlayerDto cur = resp.getTeamAPlayerList().get(i);
                TopPlayerDto nxt = resp.getTeamAPlayerList().get(k);
                if(cur.getGoal() + cur.getAssist() == nxt.getGoal() + nxt.getAssist()){
                    assertThat(cur.getRating() >= nxt.getRating()).isTrue();
                }
                else
                    assertThat(cur.getGoal() + cur.getAssist() > nxt.getGoal() + nxt.getAssist()).isTrue();

            }
        } for(int i = 0;i<resp.getTeamBPlayerList().size();i++){
            for(int k = i + 1; k<resp.getTeamBPlayerList().size();k++){
                TopPlayerDto cur = resp.getTeamBPlayerList().get(i);
                TopPlayerDto nxt = resp.getTeamBPlayerList().get(k);
                if(cur.getGoal() + cur.getAssist() == nxt.getGoal() + nxt.getAssist()){
                    assertThat(cur.getRating() >= nxt.getRating()).isTrue();
                }
                else
                    assertThat(cur.getGoal() + cur.getAssist() > nxt.getGoal() + nxt.getAssist()).isTrue();

            }
        }



    }

    @Test
    @DisplayName("챔피언스 최근 5경기 맞대결")
    public void recentRecordChampions() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        // when
        createChampionsRecord(teamA,teamB,1000,16,1,1,1);
        createChampionsRecord(teamA,teamB,1000,16,2,2,1);

        createChampionsRecord(teamA,teamB,1000,8,1,1,1);
        createChampionsRecord(teamA,teamB,1000,8,2,1,2);


        createChampionsRecord(teamA,teamB,1001,16,1,5,5);
        createChampionsRecord(teamA,teamB,1001,16,2,1,2);

        createChampionsRecord(teamA,teamB,1001,8,1,5,5);
        createChampionsRecord(teamA,teamB,1001,8,2,1,5);

        createChampionsRecord(teamA,teamB,1002,4,1,5,5);
        createChampionsRecord(teamA,teamB,1002,4,2,1,5);

        ChampionsRound championsRecord = createChampionsRecord(teamA, teamB, 1003, 2, 1, 2, 5);


        RecentRecordResponse resp = faceToHead.recentRecord(championsRecord.getId());

        // then
        List<SimpleRecordResultDto> ret = resp.getSimpleRecordResultDtoList();

        assertThat(ret.get(0).getTeamAName()).isEqualTo("testTeamA");
        assertThat(ret.get(0).getTeamBName()).isEqualTo("testTeamB");

        assertThat(ret.get(0).getScoreA()).isEqualTo(1);
        assertThat(ret.get(0).getScoreB()).isEqualTo(5);


        assertThat(ret.get(1).getScoreA()).isEqualTo(5);
        assertThat(ret.get(1).getScoreB()).isEqualTo(5);

        assertThat(ret.get(2).getScoreA()).isEqualTo(1);
        assertThat(ret.get(2).getScoreB()).isEqualTo(5);

        assertThat(ret.get(3).getScoreA()).isEqualTo(5);
        assertThat(ret.get(3).getScoreB()).isEqualTo(5);

        assertThat(ret.get(4).getScoreA()).isEqualTo(1);
        assertThat(ret.get(4).getScoreB()).isEqualTo(2);







    }



    @Test
    @DisplayName("리그 최근 5경기 맞대결")
    public void recentRecord() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        Team teamC = Team.createTeam(league,"testTeamC");
        teamRepository.save(teamC);
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        createRecord(league, teamA, teamB,1,5,2);

        createRecord(league, teamA, teamB,2,5,2);
        createRecord(league, teamA, teamB,3,5,2);


        createRecord(league,teamA,teamB,4,0,0);
        createRecord(league,teamA,teamB,5,2,5);
        createRecord(league,teamA,teamB,6,2,5);
        createRecord(league,teamA,teamB,7,2,5);
        createRecord(league,teamA,teamB,8,5,2);

        createRecord(league,teamA,teamC,9,10,10);

        // when
        LeagueRound leagueRound = new LeagueRound(league,1000,10);
        roundRepository.save(leagueRound);


        TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound, teamA);
        TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound, teamB);

        teamLeagueRecordRepository.save(tlrA);
        teamLeagueRecordRepository.save(tlrB);


        RecentRecordResponse resp = faceToHead.recentRecord(leagueRound.getId());

        assertThat(resp.getSimpleRecordResultDtoList().get(4).getTeamAName()).isEqualTo("testTeamA");
        assertThat(resp.getSimpleRecordResultDtoList().get(4).getTeamBName()).isEqualTo("testTeamB");

        assertThat(resp.getSimpleRecordResultDtoList().get(4).getScoreA()).isEqualTo(0);
        assertThat(resp.getSimpleRecordResultDtoList().get(4).getScoreB()).isEqualTo(0);

        assertThat(resp.getSimpleRecordResultDtoList().get(4).getMatchResultA()).isEqualTo(MatchResult.DRAW);
        assertThat(resp.getSimpleRecordResultDtoList().get(4).getMatchResultB()).isEqualTo(MatchResult.DRAW);




        assertThat(resp.getSimpleRecordResultDtoList().get(1).getTeamAName()).isEqualTo("testTeamA");
        assertThat(resp.getSimpleRecordResultDtoList().get(1).getTeamBName()).isEqualTo("testTeamB");

        assertThat(resp.getSimpleRecordResultDtoList().get(1).getScoreA()).isEqualTo(2);
        assertThat(resp.getSimpleRecordResultDtoList().get(1).getScoreB()).isEqualTo(5);

        assertThat(resp.getSimpleRecordResultDtoList().get(1).getMatchResultA()).isEqualTo(MatchResult.LOSE);
        assertThat(resp.getSimpleRecordResultDtoList().get(1).getMatchResultB()).isEqualTo(MatchResult.WIN);




        assertThat(resp.getSimpleRecordResultDtoList().get(0).getTeamAName()).isEqualTo("testTeamA");
        assertThat(resp.getSimpleRecordResultDtoList().get(0).getTeamBName()).isEqualTo("testTeamB");

        assertThat(resp.getSimpleRecordResultDtoList().get(0).getScoreA()).isEqualTo(5);
        assertThat(resp.getSimpleRecordResultDtoList().get(0).getScoreB()).isEqualTo(2);

        assertThat(resp.getSimpleRecordResultDtoList().get(0).getMatchResultA()).isEqualTo(MatchResult.WIN);
        assertThat(resp.getSimpleRecordResultDtoList().get(0).getMatchResultB()).isEqualTo(MatchResult.LOSE);






        // then

    }















    @Test
    @DisplayName("두팀간 전력 비교 - league ")
    public void comparisonLeague () throws Exception{

        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        createRecord(league, teamA, teamB,1,5,2);
        createRecord(league, teamA, teamB,2,5,2);
        createRecord(league, teamA, teamB,3,5,2);
        createRecord(league,teamA,teamB,4,0,0);
        createRecord(league,teamA,teamB,5,2,5);
        createRecord(league,teamA,teamB,6,2,5);
        createRecord(league,teamA,teamB,7,2,5);
        createRecord(league,teamA,teamB,8,2,5);



        // when
        LeagueRound leagueRound = new LeagueRound(league,1000,9);
        roundRepository.save(leagueRound);


        TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound, teamA);
        TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound, teamB);

        teamLeagueRecordRepository.save(tlrA);
        teamLeagueRecordRepository.save(tlrB);



        ComparisonRecordResponse comparison = faceToHead.comparison(leagueRound.getId());


        // then
        ComparisonRecordDto dtoA = comparison.getRecordList().get(0);
        ComparisonRecordDto dtoB = comparison.getRecordList().get(1);

        assertThat(dtoA.getWin()).isEqualTo(3);
        assertThat(dtoA.getDraw()).isEqualTo(1);
        assertThat(dtoA.getLose()).isEqualTo(4);

        assertThat(dtoA.getRecentWin()).isEqualTo(0);
        assertThat(dtoA.getRecentDraw()).isEqualTo(1);
        assertThat(dtoA.getRecentLose()).isEqualTo(4);

        assertThat(dtoA.getAvgScore()).isEqualTo(Math.round((23/8) * 100) / 100.0);
        assertThat(dtoA.getAvgLost()).isEqualTo(Math.round((26/8) * 100) / 100.0);





        assertThat(dtoB.getWin()).isEqualTo(4);
        assertThat(dtoB.getDraw()).isEqualTo(1);
        assertThat(dtoB.getLose()).isEqualTo(3);

        assertThat(dtoB.getRecentWin()).isEqualTo(4);
        assertThat(dtoB.getRecentDraw()).isEqualTo(1);
        assertThat(dtoB.getRecentLose()).isEqualTo(0);


        assertThat(dtoB.getAvgScore()).isEqualTo(Math.round((26/8) * 100) / 100.0);
        assertThat(dtoB.getAvgLost()).isEqualTo(Math.round((23/8) * 100) / 100.0);


    }

    private void createRecord(League league, Team teamA, Team teamB,int roundSt,int scoreA , int scoreB) {
        LeagueRound leagueRound = new LeagueRound(league,1000,roundSt);
        leagueRound.setRoundStatus(RoundStatus.DONE);
        roundRepository.save(leagueRound);
        TeamLeagueRecord tlrA = TeamLeagueRecord.create(leagueRound, teamA);
        TeamLeagueRecord tlrB = TeamLeagueRecord.create(leagueRound, teamB);


        tlrA.setScore(scoreA);
        tlrA.setOppositeScore(scoreB);


        tlrB.setScore(scoreB);
        tlrB.setOppositeScore(scoreA);


        if(scoreA > scoreB) {
            tlrA.setMatchResult(MatchResult.WIN);
            tlrB.setMatchResult(MatchResult.LOSE);
        }
        else if(scoreA == scoreB){
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

    @Test
    @DisplayName("두팀간 전력 비교 - champions")
    public void comparisonChampions() throws Exception{
        // given
        League league = new League("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testTeamA");
        Team teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        // when
        createChampionsRecord(teamA,teamB,1000,16,1,1,1);
        createChampionsRecord(teamA,teamB,1000,16,2,2,1);

        createChampionsRecord(teamA,teamB,1000,8,1,1,1);
        createChampionsRecord(teamA,teamB,1000,8,2,1,2);


        createChampionsRecord(teamA,teamB,1001,16,1,5,5);
        createChampionsRecord(teamA,teamB,1001,16,2,1,2);

        ChampionsRound championsRoundPre = createChampionsRecord(teamA,teamB,1001,8,1,5,5);


        ChampionsRound championsRound = createChampionsRecord(teamA,teamB,1001,8,2,1,5);

        ComparisonRecordResponse comparison = faceToHead.comparison(championsRoundPre.getId());

        ComparisonRecordDto dtoA = comparison.getRecordList().get(0);
        ComparisonRecordDto dtoB = comparison.getRecordList().get(1);


//        assertThat(dtoA.getWin()).isEqualTo(1);
//        assertThat(dtoA.getDraw()).isEqualTo(4);
//        assertThat(dtoA.getLose()).isEqualTo(2);

//
        assertThat(dtoA.getRecentWin()).isEqualTo(0);
        assertThat(dtoA.getRecentDraw()).isEqualTo(1);
        assertThat(dtoA.getRecentLose()).isEqualTo(1);




        assertThat(dtoB.getRecentWin()).isEqualTo(1);
        assertThat(dtoB.getRecentDraw()).isEqualTo(1);
        assertThat(dtoB.getRecentLose()).isEqualTo(0);

//
//        assertThat(dtoB.getWin()).isEqualTo(2);
//        assertThat(dtoB.getDraw()).isEqualTo(4);
//        assertThat(dtoB.getLose()).isEqualTo(1);







        // then

    }



    private ChampionsRound createChampionsRecord(Team teamA, Team teamB, int season , int roundSt, int fs  , int scoreA , int scoreB) {
        ChampionsRound championsRound = new ChampionsRound(season,roundSt,fs);
        championsRound.setRoundStatus(RoundStatus.DONE);
        roundRepository.save(championsRound);

        TeamChampionsRecord tlrA = TeamChampionsRecord.create(championsRound, teamA,fs);
        TeamChampionsRecord tlrB = TeamChampionsRecord.create(championsRound, teamB,fs);


        tlrA.setScore(scoreA);
        tlrA.setOppositeScore(scoreB);


        tlrB.setScore(scoreB);
        tlrB.setOppositeScore(scoreA);


        if(scoreA > scoreB) {
            tlrA.setMatchResult(MatchResult.WIN);
            tlrB.setMatchResult(MatchResult.LOSE);
        }
        else if(scoreA == scoreB){
            tlrA.setMatchResult(MatchResult.DRAW);
            tlrB.setMatchResult(MatchResult.DRAW);
        }
        else{
            tlrA.setMatchResult(MatchResult.LOSE);
            tlrB.setMatchResult(MatchResult.WIN);
        }

        teamChampionsRecordRepository.save(tlrA);
        teamChampionsRecordRepository.save(tlrB);
        return championsRound;
    }




}