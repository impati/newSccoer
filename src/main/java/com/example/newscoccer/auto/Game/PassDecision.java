package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.support.RandomNumber;

public class PassDecision {


    /**
     *  Total  = 900
     *
     *  pass = 400
     *  visualRange = 140
     *  sense = 120
     *  ballController = 80
     *  crosses = 70
     *  longPass = 60
     *  activeness = 50
     *  dribble = 40
     *  balance = 40
     *
     *  9개
     */


    public static int passDecision(Position position , double condition, Stat stat){
        int ret = stat.getBallControl() * 400;
        ret += stat.getCrosses() * 140;
        ret += stat.getPass() * 120;
        ret += stat.getLongPass() * 80;
        ret += stat.getDribble() * 70;
        ret += stat.getActiveness() * 60;
        ret += stat.getVisualRange() * 50;
        ret += stat.getBalance() * 40;
        ret += stat.getSense()* 40;

        // ret Max = 90000
        int ans  =  (int)(ret * (condition));

        int modValue = 2000;
        int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 3;
        int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
        int value = RandomNumber.returnRandomNumber(s , e);
        return value;
    }



    public static int passDecision(Position st, double v, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Stat stat = new Stat();
        stat.setBallControl(i);
        stat.setCrosses(i1);
        stat.setPass(i2);
        stat.setLongPass(i3);
        stat.setDribble(i4);
        stat.setActiveness(i5);
        stat.setVisualRange(i6);
        stat.setSense(i7);
        stat.setBalance(i8);
        return PassDecision.passDecision(st,v,stat);
    }
}
