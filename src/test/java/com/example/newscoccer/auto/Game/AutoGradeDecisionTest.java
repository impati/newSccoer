package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoGradeDecisionTest {


    void init(AutoGameDto dtoA,AutoGameDto dtoB){
        for(int i = 0;i<10;i++){
            Stat stat = new Stat();
            stat.update(70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,70,70,70);
            AutoPersonalData personalData =  new AutoPersonalData(0L,"testPlayer", Position.ST,stat,1.0);
            // 패스
            personalData.setPass(PassDecision.passDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 디펜스
            personalData.setDefense(DefenseDecision.defenseDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 파울
            personalData.setFoul(FoulDecision.foulDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));

            dtoA.getPlayerList().add(personalData);

        }
        for(int i = 0;i<10;i++){
            Stat stat = new Stat();
            stat.update(70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,70,70,70);
            AutoPersonalData personalData =  new AutoPersonalData(0L,"testPlayer", Position.ST,stat,1.0);
            // 패스
            personalData.setPass(PassDecision.passDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 디펜스
            personalData.setDefense(DefenseDecision.defenseDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 파울
            personalData.setFoul(FoulDecision.foulDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));

            dtoB.getPlayerList().add(personalData);
        }




        dtoA.setOpposite(dtoB);
        dtoB.setOpposite(dtoA);


        // 점유율
        dtoA.updateShare();
        dtoB.updateShare();



        // 파울
        dtoA.updateFoul();
        dtoB.updateFoul();





        // 프리킼
        dtoA.updateFreeKick();
        dtoB.updateFreeKick();




        //슈팅 골 어시
        dtoA.updateGoalAndAssist();
        dtoB.updateGoalAndAssist();

        //코너킥
        dtoA.updateCornerKick();
        dtoB.updateCornerKick();

    }
    @Test
    @DisplayName("공격수 평점")
    public void striker() throws Exception{


        AutoGameDto dtoA = new AutoGameDto(0L,"testTeam");
        AutoGameDto dtoB = new AutoGameDto(0L,"testOpposite");

        init(dtoA,dtoB);


        double avgPass = 0 , avgShooting = 0 ,avgValidShooting = 0 , avgDefense = 0 ,avgGoal  = 0 , avgAssist = 0;


        for(var ele : dtoA.getPlayerList()){
            avgPass += ele.getPass();
            avgShooting += ele.getShooting();
            avgValidShooting += ele.getValidShooting();
            avgDefense += ele.getDefense();

            avgGoal += ele.getGoal();
            avgAssist += ele.getAssist();

        }
        for(var ele : dtoB.getPlayerList()){
            avgPass += ele.getPass();
            avgShooting += ele.getShooting();
            avgValidShooting += ele.getValidShooting();
            avgDefense += ele.getDefense();
            avgGoal += ele.getGoal();
            avgAssist += ele.getAssist();
        }



        for(var ele : dtoA.getGoalAssistPairs()){
            System.out.println(" type :" + ele.getGoalType());
        }
        System.out.println("===========");
        for(var ele : dtoB.getGoalAssistPairs()){
            System.out.println(" type :" + ele.getGoalType());
        }



        avgPass = avgPass/ 20 + 1;
        avgShooting = avgShooting / 20 + 1;
        avgValidShooting = avgValidShooting / 20 + 1;
        avgDefense = avgDefense / 20 + 1;
        avgGoal /= 20;
        avgAssist /=20;







        AutoPersonalData data = dtoA.getPlayerList().get(0);
        int value = data.getGoal() * 100;
        value += data.getAssist() * 70;
        value += (data.getPass() / avgPass) * 400; // -> 200
        value += (data.getShooting() / avgShooting) * 200; // -> 100
        value += (data.getValidShooting() / avgValidShooting) * 300; // ->140
        value += (data.getDefense() /avgDefense) * 100; // ->60
        value /= 20;


        System.out.println("value = " + value);

        System.out.println("goal = " +  data.getGoal());
        System.out.println("assist = " +  data.getAssist() );
        System.out.println("pass = " + data.getPass());
        System.out.println("shooting = " + data.getShooting());
        System.out.println("validShooting = " + data.getValidShooting());
        System.out.println("defense = " + data.getDefense());


        System.out.println("avgPass = " + avgPass);
        System.out.println("avgShooting = " + avgShooting);
        System.out.println("avgValidShooting = " + avgValidShooting);
        System.out.println("avgDefense = " + avgDefense);

        System.out.println("avgGoal = " + avgGoal);
        System.out.println("avgAssist = " + avgAssist);



    }




    @Test
    @DisplayName("미드 필더  평점")
    public void mid() throws Exception{


        AutoGameDto dtoA = new AutoGameDto(0L,"testTeam");
        AutoGameDto dtoB = new AutoGameDto(0L,"testOpposite");

        init(dtoA,dtoB);


        double avgPass = 0 , avgShooting = 0 ,avgValidShooting = 0 , avgDefense = 0 ,avgGoal  = 0 , avgAssist = 0 , avgFoul = 0;


        for(var ele : dtoA.getPlayerList()){
            avgPass += ele.getPass();
            avgShooting += ele.getShooting();
            avgValidShooting += ele.getValidShooting();
            avgDefense += ele.getDefense();

            avgGoal += ele.getGoal();
            avgAssist += ele.getAssist();
            avgFoul += ele.getFoul();

        }
        for(var ele : dtoB.getPlayerList()){
            avgPass += ele.getPass();
            avgShooting += ele.getShooting();
            avgValidShooting += ele.getValidShooting();
            avgDefense += ele.getDefense();
            avgGoal += ele.getGoal();
            avgAssist += ele.getAssist();
            avgFoul += ele.getFoul();

        }



        for(var ele : dtoA.getGoalAssistPairs()){
            System.out.println(" type :" + ele.getGoalType());
        }
        System.out.println("===========");
        for(var ele : dtoB.getGoalAssistPairs()){
            System.out.println(" type :" + ele.getGoalType());
        }



        avgPass = avgPass/ 20 + 1;
        avgShooting = avgShooting / 20 + 1;
        avgValidShooting = avgValidShooting / 20 + 1;
        avgDefense = avgDefense / 20 + 1;
        avgGoal /= 20;
        avgAssist /=20;
        avgFoul /= 20 ;




        AutoPersonalData data = dtoA.getPlayerList().get(0);
        int value = data.getGoal() * 70;
        value += data.getAssist() * 100;
        value += (data.getPass() / avgPass) * 400; // -> 200
        value += (data.getShooting() / avgShooting) * 150; // -> 100
        value += (data.getValidShooting() / avgValidShooting) * 100; // ->140
        value += (data.getDefense() / avgDefense) * 350; // ->60
        value /= 20;


        System.out.println("value = " + value);

        System.out.println("goal = " +  data.getGoal());
        System.out.println("assist = " +  data.getAssist() );
        System.out.println("pass = " + data.getPass());
        System.out.println("shooting = " + data.getShooting());
        System.out.println("validShooting = " + data.getValidShooting());
        System.out.println("defense = " + data.getDefense());


        System.out.println("==============");


        System.out.println("avgFoul" + avgFoul);
        System.out.println("avgPass = " + avgPass);
        System.out.println("avgShooting = " + avgShooting);
        System.out.println("avgValidShooting = " + avgValidShooting);
        System.out.println("avgDefense = " + avgDefense);

        System.out.println("avgGoal = " + avgGoal);
        System.out.println("avgAssist = " + avgAssist);



    }

}