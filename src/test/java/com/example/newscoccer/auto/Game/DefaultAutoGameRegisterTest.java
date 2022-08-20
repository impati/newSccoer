package com.example.newscoccer.auto.Game;

import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPairDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.auto.lineUp.AutoLineUp;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.GoalType;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@Transactional
class DefaultAutoGameRegisterTest {

    @Autowired
    AutoGameRegister autoGameRegister;


    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RoundRepository roundRepository;


    @Autowired
    AutoLineUp autoLineUp;


    League league ;
    Team teamA;
    Team teamB;
    List<Player > playerList = new ArrayList<>();

    @BeforeEach
    void init(){
         league = new League("TestLeague");
        leagueRepository.save(league);

         teamA = Team.createTeam(league,"testTeamA");
         teamB = Team.createTeam(league,"testTeamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        Position[] positions = new Position[]{Position.ST,Position.RF,Position.LF,Position.RM,Position.LM,Position.AM,Position.DM,Position.CB,Position.LB,Position.RB,Position.GK};
        for (var position : positions) {
            Player playerA = Player.createPlayer("testPlayerA" + position ,position,teamA,new Stat(70));
            playerA.setMain(true);
            Player playerB = Player.createPlayer("testPlayerB" + position ,position,teamB,new Stat(70));
            playerB.setMain(true);
            playerRepository.save(playerA);
            playerRepository.save(playerB);
            playerList.add(playerA);
            playerList.add(playerB);

        }
    }
    @AfterEach
    void destory(){
        playerList.clear();
    }



    @Test
    @DisplayName("auto game Register 통합 테스트")
    public void total() throws Exception{



        LeagueRound leagueRound = new LeagueRound(league,0,1);

        roundRepository.save(leagueRound);

        TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
        TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
        teamLeagueRecordRepository.save(teamLeagueRecordA);
        teamLeagueRecordRepository.save(teamLeagueRecordB);

        autoLineUp.autoLineUp(leagueRound.getId());


        List<GoalAssistPairDto> nxt = new ArrayList<>();
        GameRecordDto ret = new GameRecordDto();

        autoGameRegister.autoGameRegisterForTest(leagueRound.getId(),ret,nxt);


        System.out.println("GameRecordDto = " + ret);
        System.out.println("GoalAssistPairDto = " + nxt);


    }


    /**
     * 평균 스텟 = 50  -> 0.98, 1.16
     *         = 70  -> 1.61 , 1.65
     *         = 100 -> 3.48 , 3.52
     * @throws Exception
     */
    @Test
    @DisplayName("평균 스코어")
    public void avgScore_() throws Exception{
        int n = 10;
        int scoreA = 0, scoreB = 0;
        for(int i = 0;i<n;i++){
            LeagueRound leagueRound = new LeagueRound(league,0,i);
            roundRepository.save(leagueRound);

            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);

            autoLineUp.autoLineUp(leagueRound.getId());


            List<GoalAssistPairDto> nxt = new ArrayList<>();
            GameRecordDto ret = new GameRecordDto();

            autoGameRegister.autoGameRegisterForTest(leagueRound.getId(),ret,nxt);


            scoreA += ret.getTeams().get(0).getScore();
            scoreB += ret.getTeams().get(1).getScore();
        }

        System.out.println("scoreA  " + (double)(scoreA/(double)n));
        System.out.println("scoreB  " + (double)(scoreB/(double)n));
    }

    @Test
    @DisplayName("승무패")
    public void winDrawLose() throws Exception{
        int n = 100;
        int win = 0, draw = 0 , lose = 0 ;
        for(int i = 0;i<n;i++){
            LeagueRound leagueRound = new LeagueRound(league,0,i);
            roundRepository.save(leagueRound);

            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);

            autoLineUp.autoLineUp(leagueRound.getId());


            List<GoalAssistPairDto> nxt = new ArrayList<>();
            GameRecordDto ret = new GameRecordDto();

            autoGameRegister.autoGameRegisterForTest(leagueRound.getId(),ret,nxt);


            int scoreA = ret.getTeams().get(0).getScore();
            int scoreB = ret.getTeams().get(1).getScore();

            if(scoreA > scoreB )win +=1;
            else if(scoreA == scoreB) draw +=1;
            else  lose +=1;
        }


        System.out.println("win = " + win);
        System.out.println("draw = " + draw);
        System.out.println("lose = " + lose);


    }


    /**         골기퍼 선방 기능 -> 상향
     * normal = 193 -> 160 -> 201 // Done
     * heading = 39 -> 34
     * longKick = 0 -> 54
     * freeKick = 47 -> 20   // Done
     * penal = 4
     *
     *
     * 현재
     * normal = 221
     * heading = 28
     * longKick = 40
     * freeKick = 44
     * penal = 3
     *
     *
     *
     * normal = 38
     * heading = 2
     * longKick = 0
     * freeKick = 35
     * penal = 2
     *
     *
     * */
    @Test
    @DisplayName("Goal Type 테스트")
    public void goalTypeTest() throws Exception{

        int n = 100;
        int arr [] = new int[5];
         // normal , heading , mid , freeKick  , Penal

        for(int i = 0;i<n;i++){
            LeagueRound leagueRound = new LeagueRound(league,0,i);
            roundRepository.save(leagueRound);

            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);

            autoLineUp.autoLineUp(leagueRound.getId());


            List<GoalAssistPairDto> nxt = new ArrayList<>();
            GameRecordDto ret = new GameRecordDto();

            autoGameRegister.autoGameRegisterForTest(leagueRound.getId(),ret,nxt);

            for(var ele : nxt){
                if(ele.getGoalType() == GoalType.NOMAL) arr[0] +=1;
                else if(ele.getGoalType() == GoalType.HEADING) arr[1] +=1;
                else if(ele.getGoalType() == GoalType.LONGKICK) arr[2] +=1;
                else if(ele.getGoalType() == GoalType.FREEKICK) arr[3] +=1;
                else if(ele.getGoalType() == GoalType.PENALTYKICK) arr[4] +=1;

            }


        }

        System.out.println("normal = " + arr[0]);
        System.out.println("heading = " + arr[1]);
        System.out.println("longKick = " + arr[2]);
        System.out.println("freeKick = " + arr[3]);
        System.out.println("penal = " + arr[4]);
    }


     @Test
    @DisplayName("기록 평균 테스트")
    public void recordTest() throws Exception{

        int n = 500;

         Map<Position,GameResultPlayerDto> positionMap = new HashMap<>();
         Position[] positions = new Position[]{Position.ST,Position.RF,Position.LF,Position.RM,Position.LM,Position.AM,Position.DM,Position.CB,Position.LB,Position.RB,Position.GK};
         for (var p : positions) {
             positionMap.put(p,new GameResultPlayerDto());
         }
         for(int i = 0;i<n;i++){
            LeagueRound leagueRound = new LeagueRound(league,0,i);
            roundRepository.save(leagueRound);

            TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(leagueRound,teamA);
            TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(leagueRound,teamB);
            teamLeagueRecordRepository.save(teamLeagueRecordA);
            teamLeagueRecordRepository.save(teamLeagueRecordB);

            autoLineUp.autoLineUp(leagueRound.getId());


            List<GoalAssistPairDto> nxt = new ArrayList<>();
            GameRecordDto ret = new GameRecordDto();

            autoGameRegister.autoGameRegisterForTest(leagueRound.getId(),ret,nxt);


            List<GameResultPlayerDto> players = ret.getPlayers();


            for (GameResultPlayerDto player : players) {
                Position position = player.getPosition();
                if(positionMap.containsKey(position)){
                    GameResultPlayerDto e = positionMap.get(position);
                    e.setDefense(e.getDefense() + player.getDefense());
                    e.setGoal(e.getGoal() + player.getGoal());
                    e.setPass(e.getPass() + player.getPass());
                    e.setShooting(e.getShooting() + player.getShooting());
                    e.setValidShooting(e.getValidShooting() + player.getValidShooting());
                    e.setFoul(e.getFoul() + player.getFoul());
                    e.setAssist(e.getAssist() + player.getAssist());
                    e.setGrade(e.getGrade() + player.getGrade());
                }
            }


        }




        for(var ele : positionMap.keySet()){

            GameResultPlayerDto playerDto = positionMap.get(ele);

            System.out.println("=================================");
            System.out.println("Position =" + ele);
            System.out.print(" goal = " + (double)(playerDto.getGoal() / (double)n));
            System.out.print(" assist = " + (double)(playerDto.getAssist() / (double)n));
            System.out.print(" defense = " + (double)(playerDto.getDefense() / (double)n));
            System.out.print(" pass = " + (double)(playerDto.getPass() / (double)n));
            System.out.print(" Shooting = " + (double)(playerDto.getShooting() / (double)n));
            System.out.print(" validShooting = " + (double)(playerDto.getValidShooting() / (double)n));
            System.out.print(" foul = " + (double)(playerDto.getFoul() / (double)n));
            System.out.println(" grade = " + (double)(playerDto.getGrade() / (double)n));
        }



    }












}