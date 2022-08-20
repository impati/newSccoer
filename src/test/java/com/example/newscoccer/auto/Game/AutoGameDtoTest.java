package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoGameDtoTest {


    @Test
    @DisplayName("normal freeKick Test")
    public void normalFreeKick() throws Exception{
        // given
        int t = 1000;
        int ans = 0;
        while(t !=0 ) {
            int totalFoul = 0;
            for (int i = 0; i < 10; i++) {
                totalFoul += FoulDecision.foulDecision(Position.ST, 1.0, 70, 70, 70, 70, 70);
            }
            // when
            totalFoul /= 10;
            int value = 50;

            while (totalFoul != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 100);
                int n = RandomNumber.returnRandomNumber(0, value);
                if (rn * 2  < n) {
                    ans += 1;
                }
                totalFoul -= 1;
            }
            t -=1;
        }
        // then
        System.out.println("평균 골수" + ( (double) ans / (double) 1000));
    }
    @Test
    @DisplayName("high freeKick Test")
    public void highFreeKick() throws Exception{
        // given
        int t = 1000;
        int ans = 0;
        int penalty = 0 ,penaltyGoal = 0;
        int shooting = 0 ,validationShooting = 0, goal = 0;
        while(t !=0 ) {
            int totalFoul = 0;
            for (int i = 0; i < 10; i++) {
                totalFoul += FoulDecision.foulDecision(Position.ST, 1.0, 70, 70, 70, 70, 70);
            }
            // when
            totalFoul /= 6;

            int value = 1000;
            while (totalFoul != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 1000);
                int n = RandomNumber.returnRandomNumber(0, value);

                if(n % 100 == 0){
                    penalty += 1;
                    if(n > 200 ) {
                        penaltyGoal +=1;
                    }
                }
                else{
                    shooting += 1;
                    if(rn < n ) validationShooting +=1;
                    if(rn *2 <n) {
                        goal+=1;
                    }
                }
                totalFoul -= 1;
            }
            t -=1;
        }
        // then
        System.out.println("penalty = " + ((double)penalty) / 1000 ) ;
        System.out.println("penaltyGoal = " + ((double)penaltyGoal) / 1000 ) ;
        System.out.println("shooting = " + ((double)shooting) / 1000 ) ;
        System.out.println("validationShooting = " + ((double)validationShooting) / 1000 ) ;
        System.out.println("goal = " + ((double)goal) / 1000 ) ;
    }




    @Test
    @DisplayName("점유율 테스트 5 : 5")
    public void shareTest() throws Exception{
        int t = 1000;

        double avgShare = 0;
        while(t!=0) {

            int passSumA = 0, passSumB = 0, defenseA = 0, defenseB = 0;
            for (int i = 0; i < 10; i++)
                passSumA += PassDecision.passDecision(Position.ST, 1.0, 70, 70, 70, 70, 70, 70, 70, 70, 70);
            for (int i = 0; i < 10; i++)
                passSumB += PassDecision.passDecision(Position.ST, 1.0, 70, 70, 70, 70, 70, 70, 70, 70, 70);


            for (int i = 0; i < 10; i++)
                defenseA += DefenseDecision.defenseDecision(Position.ST, 1.0, 70, 70, 70, 70,
                        70, 70, 70, 70, 70, 70, 70, 70, 70, 70);
            for (int i = 0; i < 10; i++)
                defenseB += DefenseDecision.defenseDecision(Position.ST, 1.0, 70, 70, 70, 70,
                        70, 70, 70, 70, 70, 70, 70, 70, 70, 70);

            int result1 = passSumA + defenseA;
            int result2 = passSumB + defenseB;
            double share = ((double) result1 / (result1 + result2)) * 100;
            avgShare += share;
            t-=1;
        }

        System.out.println("Avg share = " + avgShare / 1000);
        // then

    }

    @Test
    @DisplayName("점유율 테스트 ")
    public void shareTest1() throws Exception{
        int t = 1000;

        double avgShare = 0;
        while(t!=0) {

            int passSumA = 0, passSumB = 0, defenseA = 0, defenseB = 0;
            for (int i = 0; i < 10; i++)
                passSumA += PassDecision.passDecision(Position.ST, 1.0, 70, 70, 70, 70, 70, 70, 70, 70, 70);
            for (int i = 0; i < 10; i++)
                passSumB += PassDecision.passDecision(Position.ST, 1.0, 40, 40, 40, 40, 40, 40, 40, 40, 40);

            for (int i = 0; i < 10; i++)
                defenseA += DefenseDecision.defenseDecision(Position.ST, 1.0, 70, 70, 70, 70,
                        70, 70, 70, 70, 70, 70, 70, 70, 70, 70);
            for (int i = 0; i < 10; i++)
                defenseB += DefenseDecision.defenseDecision(Position.ST, 1.0, 70, 70, 70, 70,
                        40, 40, 40, 40, 40, 40, 40, 40, 40, 40);

            int result1 = passSumA + defenseA;
            int result2 = passSumB + defenseB;
            double share = ((double) result1 / (result1 + result2)) * 100;
            avgShare += share;
            t-=1;
        }

        System.out.println("Avg share = " + avgShare / 1000);
        // then

    }







    @Test
    @DisplayName("updateGoalAndAssist")
    public void updateGoalAndAssist() throws Exception{

        int t = 1000;
        int normal = 0, heading = 0 , mid = 0;
        int passSum = 0;
        int shooting = 0;
        while(t!=0) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += PassDecision.passDecision(Position.ST, 1.0,
                        70, 70, 70, 70, 70, 70, 70, 70, 70);
            }
            passSum += sum;
            int pass = sum / 10;

            Stat stat = new Stat();
            stat.update(70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,
                    70,70,70,70,70,70,70,70,70,70,70,70);

            AutoPersonalData ele = new AutoPersonalData(0L,"test",Position.ST,stat,1.0);


            int normalCount = ele.normalShooting(pass / 5);
            while (normalCount != 0) {
                normal += 1;
                normalCount -=1;
            }
            int headingCount = ele.heading(pass / 10);
            while(headingCount !=0 ){
                heading +=1;
                headingCount -=1;
            }
            int midCount  = ele.midShooting(pass/10);
            while(midCount !=0){
                mid+=1;
                midCount -=1;
            }
            shooting += ele.getShooting();
            t -= 1;
        }


        System.out.println("shooting = "  + ((double)shooting / 1000));
        System.out.println("passSum = " + ((double)passSum / 1000));
        System.out.println("normal " + ((double)normal /  1000) );
        System.out.println("heading " + ((double)heading /1000));
        System.out.println("mid " + ((double) mid / 1000));

    }



    @Test
    @DisplayName("코너킥")
    public void cornerKick() throws Exception{

        int t =  1000;
        int shooting = 0 , validShooting = 0 , goal = 0;
        int value = 600;
        while(t != 0){
            int rn = RandomNumber.returnRandomNumber(0,600);
            int n =  RandomNumber.returnRandomNumber(0,600);
            if(rn < n) shooting += 1;
            if(rn * 2 < n) validShooting += 1;
            if(rn * 3 < n) {
                goal += 1;
            }

            t -= 1;
        }



        System.out.println("shooting = " + ((double)shooting) / 1000 ) ;
        System.out.println("validationShooting = " + ((double)validShooting) / 1000 ) ;
        System.out.println("goal = " + ((double)goal) / 1000 ) ;

    }




    @Test
    @DisplayName("isSuperSave")
    public void superSave() throws Exception{
        AutoPersonalData p = new AutoPersonalData(0L,"test",Position.GK,new Stat(70),1.0);
        int value = p.goalKeeperStat();

        int total = 1000 , count = 0 ;
        int avgValue = 0;
        for(int i = 0 ;i<total ;i++){
            int rn = RandomNumber.returnRandomNumber(0, 1000);
            int n = RandomNumber.returnRandomNumber(0, value);
            if(rn * 2 < n) count +=1;
            avgValue += value;
        }


        System.out.println("superSave P = "  + (double)(count/(double)1000));


    }















}