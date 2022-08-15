package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.support.RandomNumber;

public class FoulDecision {

    /**
     *
     *  Total = 100000 점에서
     *
     *  dense = 400
     *  tackle = 200
     *  intercepting = 200
     *  slidingTackle = 150
     *  sense = 50
     *
     *
     *
     *
     */
    public static int foulDecision(Position position , double condition , Stat stat){
        //위험성
        int maxNum = RandomNumber.returnRandomNumber(600,700);

        // 능력치
        int ret = stat.getDefense() * 400;
        ret += stat.getTackle() * 200;
        ret += stat.getIntercepting() * 200;
        ret += stat.getSlidingTackle() * 150;
        ret += stat.getSense() * 50;
        // ret = max 50000
        int ans  = (int) ((ret / 500) * condition);
        int curValue = maxNum - ans;
        if(curValue < 0 ) return 0;
        else{
            int modValue = RandomNumber.returnRandomNumber(ret/1000 + 1,2 * (ret/1000) + 1);
            return RandomNumber.returnRandomNumber(0,curValue / modValue);
        }
    }

    public static int foulDecision(Position st, double v, int i, int i1, int i2, int i3, int i4) {
        Stat stat = new Stat();
        stat.setDefense(i);
        stat.setTackle(i1);
        stat.setIntercepting(i2);
        stat.setSlidingTackle(i3);
        stat.setSense(i4);
        return foulDecision(st,v,stat);
    }
}
