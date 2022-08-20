package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;

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

    /**
     *  공격수 :
     *  high   :minValue 1
     *         :maxValue 64
     *         :avg 25
     *
     *  normal :minValue 1
     *         :maxValue 41
     *         :avg 17
     *
     *  lower  :minValue 1
     *         :maxValue 23
     *         :avg 10
     *
     *  미드 필더
     *  high :minValue 4
     *       :maxValue 97
     *       :avg 39
     *
     *  normal :minValue 3
     *         :maxValue 70
     *         :avg 28
     *
     *  lower :minValue 3
     *        :maxValue 39
     *        :avg 17
     *
     *
     *  수비수
     *
     *  high :minValue 3
     *       :maxValue 87
     *       :avg 36
     *
     *  normal :minValue 3
     *         :maxValue 60
     *         :avg 26
     *
     *  lower :minValue 3
     *        :maxValue 38
     *        :avg 16
     *
     *
     *  골기퍼
     *  normal :minValue 0
     *         :maxValue 10
     *         :avg 3
     *
     *  lower :minValue 0
     *        :maxValue 5
     *        :avg 1
     *
     *
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
        return AutoPositionUtils.passUtil(position,ans);
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
