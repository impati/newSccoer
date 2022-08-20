package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoPersonalDataTest {



    @Test
    @DisplayName("normalShooting")
    public void normalShooting() throws Exception{

        int pass = 6;
        int value = 1000;
        int shooting = 0 , validShooting = 0 , goal = 0;


        int t = 1000;

        while(t != 0) {
            pass = 6;
            while (pass != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 1500);
                int n = RandomNumber.returnRandomNumber(0, value);
                if(rn * 2  < n ) shooting += 1;
                if (rn * 4 < n) validShooting += 1;
                if (rn * 6 < n) goal += 1;
                pass -= 1;
            }
            t -=1;
        }

        System.out.println("shooting avg =" + shooting / (double)1000);
        System.out.println("validShooting avg =" + validShooting / (double)1000);
        System.out.println("goal avg =" + goal / (double)1000);

    }

    @Test
    @DisplayName("공격수 ")
    public void 공격수() throws Exception{

        Stat stat = new Stat(100);

        int shooting = 0 ,validShooting = 0 , goal = 0 , ret = 0 , n = 1000;
        for(int i = 0 ;i<n;i++) {
            int passSum = 0;
            passSum += PassDecision.passDecision(Position.ST, 1.0, stat);
            passSum += PassDecision.passDecision(Position.RF, 1.0, stat);
            passSum += PassDecision.passDecision(Position.LF, 1.0, stat);

            passSum += PassDecision.passDecision(Position.AM, 1.0, stat);
            passSum += PassDecision.passDecision(Position.RM, 1.0, stat);
            passSum += PassDecision.passDecision(Position.LM, 1.0, stat);

            passSum += PassDecision.passDecision(Position.DM, 1.0, stat);
            passSum += PassDecision.passDecision(Position.RB, 1.0, stat);
            passSum += PassDecision.passDecision(Position.LB, 1.0, stat);

            passSum += PassDecision.passDecision(Position.CB, 1.0, stat);
            passSum += PassDecision.passDecision(Position.GK, 1.0, stat);


            int pass = passSum / 10;
            AutoPersonalData ele = new AutoPersonalData();
            ret += AutoPositionUtils.headingAndMidGoalUtil(ele, Position.CB, pass / 10, 1000);
            shooting += ele.getShooting();
            validShooting += ele.getValidShooting();
            goal += ele.getGoal();
        }

        System.out.println("shooting avg =" + shooting / (double)1000);
        System.out.println("validShooting avg =" + validShooting / (double)1000);
        System.out.println("goal avg =" + goal / (double)1000);

    }














}