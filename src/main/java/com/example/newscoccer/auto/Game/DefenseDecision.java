package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;

public class DefenseDecision {


    /**
     * Total = 100000
     *
     *  dense = 300
     *  intercepting = 130
     *  tackle = 70
     *  slidingTackle = 50
     *
     *  acceleration = 40
     *  speed = 40
     *  physicalFight 70
     *  stamina = 50
     *  activeness = 50
     *  balance = 40
     *  sense = 50
     *  speedReaction = 30
     *  visualRange = 30
     *  positioning = 50
     * 14 개
     */


    /**
     * 공격수
     *   :minValue 1
     *   :maxValue 20
     *   :avg 8
     *
     *   :minValue 1
     *   :maxValue 15
     *   :avg 6
     *
     *   :minValue 1
     *   :maxValue 9
     *   :avg 3
     *
     *
     * 미드 필더
     *  :minValue 1
     *  :maxValue 21
     *  :avg 8
     *
     *  :minValue 1
     *  :maxValue 14
     *  :avg 6
     *
     *  :minValue 1
     *  :maxValue 9
     *  :avg 3
     *
     * 수비수
     *   :minValue 3
     *   :maxValue 49
     *   :avg 20
     *
     *   :minValue 3
     *   :maxValue 33
     *   :avg 15
     *
     *   :minValue 3
     *   :maxValue 21
     *   :avg 9
     *
     * 골기퍼
     *   :minValue 0
     *   :maxValue 7
     *   :avg 2
     *
     *   :minValue 0
     *   :maxValue 4
     *   :avg 1
     *
     */
    public static int defenseDecision(Position position , double condition , Stat stat){
        // 능력치
        int ret = stat.getDefense() * 300;
        ret += stat.getTackle() * 70;
        ret += stat.getIntercepting() * 130;
        ret += stat.getSlidingTackle() * 50;
        ret += stat.getSense() * 50;
        ret += stat.getAcceleration() * 40;
        ret += stat.getSpeed() * 40;
        ret += stat.getPhysicalFight() * 70;
        ret += stat.getStamina() * 50;
        ret += stat.getActiveness() * 50;
        ret += stat.getBalance() * 40;
        ret += stat.getSpeedReaction() * 30;
        ret += stat.getVisualRange() * 30;
        ret += stat.getPositioning()* 50;
        // ret = max 1400000

        int ans  =(int)(ret * (condition));

        return AutoPositionUtils.defenseUtil(position,ans);


    }

    // 사용 x
    public static int defenseDecision(Position st, double v, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        Stat stat = new Stat();
        stat.setPositioning(i);
        stat.setVisualRange(i1);
        stat.setSpeedReaction(i2);
        stat.setBalance(i3);
        stat.setActiveness(i4);
        stat.setStamina(i5);
        stat.setPhysicalFight(i6);
        stat.setSpeed(i7);
        stat.setAcceleration(i8);
        stat.setSense(i9);
        stat.setSlidingTackle(i10);
        stat.setTackle(i11);
        stat.setIntercepting(i12);
        stat.setDefense(i13);
        return DefenseDecision.defenseDecision(st,v,stat);
    }
}
