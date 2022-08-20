package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import lombok.Data;

@Data
public class AutoPersonalData {
    private Long playerId;//
    private String playerName;
    private Position participatePosition;//
    private Stat stat;//
    private double condition;//

    private int pass;//
    private int goal;//
    private int assist;//
    private int shooting;//
    private int validShooting;//

    private int foul;//
    private int defense;//

    private int grade;

    public AutoPersonalData(Long playerId,String name, Position participatePosition, Stat stat,double condition) {
        this.playerId = playerId;
        this.playerName = name;
        this.participatePosition = participatePosition;
        this.stat = stat;
        this.condition = condition;
    }

    public AutoPersonalData() {
    }



    /**
     * 공격수
     * shooting avg =0.409
     * validShooting avg =0.324
     * goal avg =0.181
     *
     * 미드 필더
     * shooting avg =0.346
     * validShooting avg =0.261
     * goal avg =0.142
     *
     * 수비수
     *shooting avg =0.061
     * validShooting avg =0.045
     * goal avg =0.018
     *
     */


    public int midShooting(int receivedPass){
        int value = stat.getShootPower();
        value += stat.getGoalDetermination()*2;
        value += stat.getMidRangeShot()*5;
        value += stat.getSense();
        value += stat.getPositioning();

        receivedPass /= 6;
        int ans = (int)(value * condition);
        return AutoPositionUtils.headingAndMidGoalUtil(this,this.getParticipatePosition(),receivedPass,ans);


    }


    public int heading(int receivedPass){
        int value = stat.getHeading()*5;
        value += stat.getSense();
        value += stat.getPositioning();
        value += stat.getGoalDetermination();
        value += stat.getJump()*2;

        receivedPass /= 4;
        int ans = (int)(value * condition);
        return AutoPositionUtils.headingAndMidGoalUtil(this,this.getParticipatePosition(),receivedPass,ans);

    }


    /**
     *
     * value = 1000
     * 공격수
     *  :shooting avg =1.336
     *  :validShooting avg =0.676
     *  :goal avg =0.45
     * 미드 필더
     *  :shooting avg =1.085
     *  :validShooting avg =0.538
     *  :goal avg =0.367
     * 수비수
     *  :shooting avg =0.245
     *  :validShooting avg =0.109
     *  :goal avg =0.071
     * 골기퍼
     * :shooting avg =0.011
     * :validShooting avg =0.006
     * :goal avg =0.005
     *
     */
    public int normalShooting(int receivedPass){
        int value = stat.getActiveness();
        value += stat.getAcceleration()*2;
        value += stat.getSpeed();
        value += stat.getPhysicalFight();
        value += stat.getStamina();
        value += stat.getActiveness();
        value += stat.getBalance();
        value += stat.getBallControl();
        value += stat.getDribble() *2;
        value += stat.getMidRangeShot();
        value += stat.getShootPower();
        value += stat.getSense();
        value += stat.getVisualRange();
        value += stat.getPositioning();
        value += stat.getGoalDetermination()*4;
        // max = 2000

        receivedPass /= 5;
        int ans = (int)(value * condition);
        return AutoPositionUtils.normalGoalUtil(this,this.getParticipatePosition(),receivedPass,ans);
    }


    /**
     * 골기퍼가 막는데 사용하는 스텟.
     */
    public int goalKeeperStat(){
        int value = 0;
        Stat goalKeeperStat = this.getStat();
        value += goalKeeperStat.getDiving();
        value += goalKeeperStat.getHandling();
        value += goalKeeperStat.getGoalKick();
        value += goalKeeperStat.getSpeedReaction() * 3;
        value += goalKeeperStat.getPositioning() * 2;
        value += goalKeeperStat.getVisualRange();
        value += goalKeeperStat.getSense();
        return (int)(value * condition); // max = 1000
    }

}
