package com.example.newscoccer.SearchService.player.info.league;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultPlayerLeagueInfoTest {

    @Autowired
    EntityRecordInfo<PlayerLeagueInfoRequest,PlayerLeagueInfoResponse> playerLeagueInfo ;


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
    @DisplayName("선수 리그 기록")
    public void playerLeague() throws Exception{

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
                lastRank = rank;
                lastRating = rating;

            }



            LeagueRound leagueRound = new LeagueRound(league,0,i);
            roundRepository.save(leagueRound);
            plr.setRound(leagueRound);
            leagueRecordRepository.save(plr);
        }
        avgGrade /= SeasonUtils.lastLeagueRoundSt;

        // when


        PlayerLeagueInfoResponse resp = playerLeagueInfo.recordInfo(new PlayerLeagueInfoRequest(player.getId(),0));
        // then
        assertThat(resp.getAssist()).isEqualTo(totalAssist);
        assertThat(resp.getGoal()).isEqualTo(totalGoal);
        assertThat(resp.getPass()).isEqualTo(totalPass);
        assertThat(resp.getShooting()).isEqualTo(totalShooting);
        assertThat(resp.getValidShooting()).isEqualTo(totalValidShooting);
        assertThat(resp.getFoul()).isEqualTo(totalFoul);
        assertThat(resp.getDefense()).isEqualTo(totalGoodDefense);
        
        assertThat(resp.getWin()).isEqualTo(winTotal);
        assertThat(resp.getDraw()).isEqualTo(drawTotal);
        assertThat(resp.getLose()).isEqualTo(loseTotal);
        
        assertThat(resp.getGameNumber()).isEqualTo(SeasonUtils.lastLeagueRoundSt);

        assertThat(resp.getIsBest()).isEqualTo(isBest);
        assertThat(resp.getRank()).isEqualTo(lastRank);


        assertThat(resp.getRating()).isEqualTo(lastRating);

        assertThat(resp.getAvgGrade()).isEqualTo(Math.round(avgGrade * 100) / 100.0);

        
    }
}