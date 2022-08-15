package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.support.RandomNumber;
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



    public int midShooting(int receivedPass){
        int value = stat.getShootPower();
        value += stat.getGoalDetermination()*2;
        value += stat.getMidRangeShot()*5;
        value += stat.getSense();
        value += stat.getPositioning();

        receivedPass /= 3;
        int ret = 0;
        while(receivedPass != 0){
            int rn = RandomNumber.returnRandomNumber(0, 1000);
            int n = RandomNumber.returnRandomNumber(0, value);
            if(rn * 3 < n) this.setShooting(this.getShooting() + 1);
            if(rn * 4 < n) this.setValidShooting(this.getValidShooting() + 1);
            if (rn * 7 < n) {
                ret +=1;
                this.setGoal(this.getGoal() + 1);
            }
            receivedPass -=1;
        }

        return ret;


    }


    public int heading(int receivedPass){
        int value = stat.getHeading()*5;
        value += stat.getSense();
        value += stat.getPositioning();
        value += stat.getGoalDetermination();
        value += stat.getJump()*2;

        receivedPass /= 3;
        int ret = 0;
        while(receivedPass != 0){
            int rn = RandomNumber.returnRandomNumber(0, 1000);
            int n = RandomNumber.returnRandomNumber(0, value);
            if(rn * 3 < n) this.setShooting(this.getShooting() + 1);
            if(rn * 4 < n) this.setValidShooting(this.getValidShooting() + 1);
            if (rn * 7 < n) {
                ret +=1;
                this.setGoal(this.getGoal() + 1);
            }
            receivedPass -=1;
        }

        return ret;

    }
    
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
        int ret = 0;
        while(receivedPass != 0){
            int rn = RandomNumber.returnRandomNumber(0, 1500);
            int n = RandomNumber.returnRandomNumber(0, value);
            if(rn * 2 < n) this.setShooting(this.getShooting() + 1);
            if(rn * 4 < n) this.setValidShooting(this.getValidShooting() + 1);
            if (rn * 6 < n) {
                ret +=1;
                this.setGoal(this.getGoal() + 1);
            }
            receivedPass -=1;
        }

        return ret;
    }


}
