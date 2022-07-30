package com.example.newscoccer.SearchService.player.info.totalInfo;

import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultPlayerTotalInfoTest {

    @Autowired
    EntityTotalInfo<PlayerTotalInfoRequest, PlayerTotalInfoResponse> recordInfo;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;

    @Autowired
    RoundRepository roundRepository;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerLeagueRecordRepository leagueRecordRepository;



    @Test
    @DisplayName("선수 전체 기록 테스트 - 리그 , 챔피언스리그 ")
    public void leagueOrChampions() throws Exception{
        // given
        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();
        League league = maker.getLeague();
        Player player = maker.getPlayer();
        Team team = maker.getTeam();

        int totalGoal = 0;
        int totalAssist = 0;
        int totalPass = 0;
        int totalShooting = 0;
        int totalValidShooting = 0;
        int totalFoul = 0;
        int totalGoodDefense = 0;

        double avgGrade = 0;

        double lastRating = 0;
        int lastRank = 0;

        int winTotal = 0;
        int drawTotal = 0;
        int loseTotal = 0;
        int isBest = 0;

        for(int i = 1; i<= SeasonUtils.lastLeagueRoundSt; i++) {
            PlayerLeagueRecord plr = new PlayerLeagueRecord();
            plr.setPlayer(player);
            plr.setTeam(team);



            int goal = RandomNumber.returnRandomNumber(0,5);
            totalGoal += goal;

            int assist = RandomNumber.returnRandomNumber(0,5);
            totalAssist += assist;

            int pass = RandomNumber.returnRandomNumber(0,5);
            totalPass += pass;
            int shooting = RandomNumber.returnRandomNumber(0,5);
            totalShooting += shooting;

            int validShooting = RandomNumber.returnRandomNumber(0,5);
            totalValidShooting += validShooting;
            int foul = RandomNumber.returnRandomNumber(0,5);

            totalFoul += foul;

            int defense = RandomNumber.returnRandomNumber(0,5);
            totalGoodDefense += defense;

            int rank = RandomNumber.returnRandomNumber(1,16);

            double rating = RandomNumber.returnRandomNumber(1500,2000);

            int grade = RandomNumber.returnRandomNumber(0 , 100);
            avgGrade += grade;

            int rn = RandomNumber.returnRandomNumber(1,3);
            MatchResult matchResult = null;
            if(rn == 1) {
                winTotal++;
                matchResult = MatchResult.WIN;
            }
            else if(rn == 2){
                drawTotal++;
                matchResult = MatchResult.DRAW;
            }
            else{
                loseTotal++;
                matchResult = MatchResult.LOSE;
            }

            int best = RandomNumber.returnRandomNumber(0,100);
            if(best < 30){
                isBest += 1;
                plr.setBest(true);
            }
            else plr.setBest(false);



            plr.setMatchResult(matchResult);
            plr.setGrade(grade);
            plr.setGoal(goal);
            plr.setAssist(assist);
            plr.setPass(pass);
            plr.setShooting(shooting);
            plr.setValidShooting(validShooting);
            plr.setFoul(foul);
            plr.setGoodDefense(defense);
            plr.setRank(rank);
            plr.setRating(rating);

            if(i == SeasonUtils.lastLeagueRoundSt){
                lastRank = 1;
                lastRating = rating;
                plr.setRank(3);
            }



            LeagueRound leagueRound = new LeagueRound(league,0,i);
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            plr.setRound(leagueRound);
            leagueRecordRepository.save(plr);
        }


        for(int i = 1; i<= SeasonUtils.lastLeagueRoundSt; i++) {
            PlayerLeagueRecord plr = new PlayerLeagueRecord();
            plr.setPlayer(player);
            plr.setTeam(team);



            int goal = RandomNumber.returnRandomNumber(0,5);
            totalGoal += goal;

            int assist = RandomNumber.returnRandomNumber(0,5);
            totalAssist += assist;

            int pass = RandomNumber.returnRandomNumber(0,5);
            totalPass += pass;
            int shooting = RandomNumber.returnRandomNumber(0,5);
            totalShooting += shooting;

            int validShooting = RandomNumber.returnRandomNumber(0,5);
            totalValidShooting += validShooting;
            int foul = RandomNumber.returnRandomNumber(0,5);

            totalFoul += foul;

            int defense = RandomNumber.returnRandomNumber(0,5);
            totalGoodDefense += defense;

            int rank = RandomNumber.returnRandomNumber(1,16);

            double rating = RandomNumber.returnRandomNumber(1500,2000);

            int grade = RandomNumber.returnRandomNumber(0 , 100);
            avgGrade += grade;

            int rn = RandomNumber.returnRandomNumber(1,3);
            MatchResult matchResult = null;
            if(rn == 1) {
                winTotal++;
                matchResult = MatchResult.WIN;
            }
            else if(rn == 2){
                drawTotal++;
                matchResult = MatchResult.DRAW;
            }
            else{
                loseTotal++;
                matchResult = MatchResult.LOSE;
            }

            int best = RandomNumber.returnRandomNumber(0,100);
            if(best < 30){
                isBest += 1;
                plr.setBest(true);
            }
            else plr.setBest(false);



            plr.setMatchResult(matchResult);
            plr.setGrade(grade);
            plr.setGoal(goal);
            plr.setAssist(assist);
            plr.setPass(pass);
            plr.setShooting(shooting);
            plr.setValidShooting(validShooting);
            plr.setFoul(foul);
            plr.setGoodDefense(defense);
            plr.setRank(rank);
            plr.setRating(rating);

            if(i == SeasonUtils.lastLeagueRoundSt){
                lastRank = 1;
                lastRating = rating;
                plr.setRank(1);
            }



            LeagueRound leagueRound = new LeagueRound(league,1,i);
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            plr.setRound(leagueRound);
            leagueRecordRepository.save(plr);
        }




        for(int i = 1; i<= SeasonUtils.lastLeagueRoundSt; i++) {
            PlayerChampionsRecord plr = new PlayerChampionsRecord();
            plr.setPlayer(player);
            plr.setTeam(team);



            int goal = RandomNumber.returnRandomNumber(0,5);
            totalGoal += goal;

            int assist = RandomNumber.returnRandomNumber(0,5);
            totalAssist += assist;

            int pass = RandomNumber.returnRandomNumber(0,5);
            totalPass += pass;
            int shooting = RandomNumber.returnRandomNumber(0,5);
            totalShooting += shooting;

            int validShooting = RandomNumber.returnRandomNumber(0,5);
            totalValidShooting += validShooting;
            int foul = RandomNumber.returnRandomNumber(0,5);

            totalFoul += foul;

            int defense = RandomNumber.returnRandomNumber(0,5);
            totalGoodDefense += defense;

            int rank = RandomNumber.returnRandomNumber(1,16);

            double rating = RandomNumber.returnRandomNumber(1500,2000);

            int grade = RandomNumber.returnRandomNumber(0 , 100);
            avgGrade += grade;



            int rn = RandomNumber.returnRandomNumber(1,3);
            MatchResult matchResult = null;
            if(rn == 1) {
                winTotal++;
                matchResult = MatchResult.WIN;
            }
            else if(rn == 2){
                drawTotal++;
                matchResult = MatchResult.DRAW;
            }
            else{
                loseTotal++;
                matchResult = MatchResult.LOSE;
            }

            int best = RandomNumber.returnRandomNumber(0,100);
            if(best < 30){
                isBest += 1;
                plr.setBest(true);
            }
            else plr.setBest(false);



            plr.setMatchResult(matchResult);
            plr.setGrade(grade);
            plr.setGoal(goal);
            plr.setAssist(assist);
            plr.setPass(pass);
            plr.setShooting(shooting);
            plr.setValidShooting(validShooting);
            plr.setFoul(foul);
            plr.setGoodDefense(defense);
            plr.setRank(9999);
            plr.setRating(rating);

            if(i == 1){
               plr.setRank(4);
            }



            ChampionsRound leagueRound = new ChampionsRound(0,i,RandomNumber.returnRandomNumber(0,1));
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            plr.setRound(leagueRound);
            playerChampionsRecordRepository.save(plr);
        }





        for(int i = 1; i<= SeasonUtils.lastLeagueRoundSt; i++) {
            PlayerChampionsRecord plr = new PlayerChampionsRecord();
            plr.setPlayer(player);
            plr.setTeam(team);



            int goal = RandomNumber.returnRandomNumber(0,5);
            totalGoal += goal;

            int assist = RandomNumber.returnRandomNumber(0,5);
            totalAssist += assist;

            int pass = RandomNumber.returnRandomNumber(0,5);
            totalPass += pass;
            int shooting = RandomNumber.returnRandomNumber(0,5);
            totalShooting += shooting;

            int validShooting = RandomNumber.returnRandomNumber(0,5);
            totalValidShooting += validShooting;
            int foul = RandomNumber.returnRandomNumber(0,5);

            totalFoul += foul;

            int defense = RandomNumber.returnRandomNumber(0,5);
            totalGoodDefense += defense;

            int rank = RandomNumber.returnRandomNumber(1,16);

            double rating = RandomNumber.returnRandomNumber(1500,2000);

            int grade = RandomNumber.returnRandomNumber(0 , 100);
            avgGrade += grade;



            int rn = RandomNumber.returnRandomNumber(1,3);
            MatchResult matchResult = null;
            if(rn == 1) {
                winTotal++;
                matchResult = MatchResult.WIN;
            }
            else if(rn == 2){
                drawTotal++;
                matchResult = MatchResult.DRAW;
            }
            else{
                loseTotal++;
                matchResult = MatchResult.LOSE;
            }

            int best = RandomNumber.returnRandomNumber(0,100);
            if(best < 30){
                isBest += 1;
                plr.setBest(true);
            }
            else plr.setBest(false);



            plr.setMatchResult(matchResult);
            plr.setGrade(grade);
            plr.setGoal(goal);
            plr.setAssist(assist);
            plr.setPass(pass);
            plr.setShooting(shooting);
            plr.setValidShooting(validShooting);
            plr.setFoul(foul);
            plr.setGoodDefense(defense);
            plr.setRank(9999);
            plr.setRating(rating);

            if(i == 1){
                plr.setRank(2);
            }



            ChampionsRound leagueRound = new ChampionsRound(1,i,RandomNumber.returnRandomNumber(0,1));
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);
            plr.setRound(leagueRound);
            playerChampionsRecordRepository.save(plr);
        }



        avgGrade /= 180;



        // when
        PlayerTotalInfoResponse resp = recordInfo.totalInfo(new PlayerTotalInfoRequest(player.getId()));
        // then
        assertThat(resp.getAssist()).isEqualTo(totalAssist);
        assertThat(resp.getGoal()).isEqualTo(totalGoal);
        assertThat(resp.getPass()).isEqualTo(totalPass);
        assertThat(resp.getShooting()).isEqualTo(totalShooting);
        assertThat(resp.getValidShooting()).isEqualTo(totalValidShooting);
        assertThat(resp.getFoul()).isEqualTo(totalFoul);
          assertThat(resp.getWin()).isEqualTo(winTotal);
        assertThat(resp.getDraw()).isEqualTo(drawTotal);
        assertThat(resp.getLose()).isEqualTo(loseTotal);
        assertThat(resp.getGameNumber()).isEqualTo(180);
        assertThat(resp.getIsBest()).isEqualTo(isBest);
        assertThat(resp.getAvgGrade()).isEqualTo(Math.round(avgGrade * 100) / 100.0);

        Assertions.assertThat(resp.getLeagueFirst()).isEqualTo(1);
        Assertions.assertThat(resp.getLeagueSecond()).isEqualTo(0);
        Assertions.assertThat(resp.getLeagueThird()).isEqualTo(1);
        Assertions.assertThat(resp.getLeagueFour()).isEqualTo(0);

        Assertions.assertThat(resp.getChampionsFirst()).isEqualTo(0);
        Assertions.assertThat(resp.getChampionsSecond()).isEqualTo(1);
        Assertions.assertThat(resp.getChampionsThird()).isEqualTo(0);
        Assertions.assertThat(resp.getChampionsFour()).isEqualTo(1);



    }
}