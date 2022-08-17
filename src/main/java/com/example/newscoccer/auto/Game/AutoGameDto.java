package com.example.newscoccer.auto.Game;

import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPairDto;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.record.GoalType;
import com.example.newscoccer.support.RandomNumber;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class AutoGameDto {
    private Long teamId;//
    private String teamName;//

    public AutoGameDto(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    private List<AutoPersonalData> playerList = new ArrayList<>();//


    private int passSum ;//
    private double share;//


    private int totalFoul;//

    private int score;
    private int cornerKick;//

    private int freeKick; //

    private AutoGameDto opposite;//

    private List<GoalAssistPairDto> goalAssistPairs = new ArrayList<>();


    // avg pass , avg dense 세팅 -> 점유율 값을 세팅
    public void updateShare(){
        if(playerList.size() == 0)return;
        this.passSum +=  playerList.stream().mapToInt(ele -> ele.getPass()).sum();
        int result1 =  passSum + playerList.stream().mapToInt(ele -> ele.getDefense()).sum() ;
        int result2 = (opposite.getPlayerList().stream().mapToInt(ele->ele.getPass()).sum() + opposite.getPlayerList().stream().mapToInt(ele->ele.getDefense()).sum());
        this.share = ((double) result1 /  (result1 + result2)) * 100;
    }

    // foul 세팅
    public void updateFoul(){
        this.totalFoul = playerList.stream().mapToInt(ele->ele.getFoul()).sum();
    }
    // 상대 foul -> 나의 프로킥 기회


    // 경기당 프리킥 골 확률 -> 0.7 //
    public void updateFreeKick(){
        int oppositeFoul = opposite.getTotalFoul();
        oppositeFoul /= 5;
        this.setFreeKick(oppositeFoul);
        AutoPersonalData freeKicker = decisionFreeKicker();
        // 점수가 높을 수록 유리한 입지를 얻을 수 있어야 합니다 .
        int value = freeKicker.getStat().getGoalDetermination()* 4 + freeKicker.getStat().getShootPower() + freeKicker.getStat().getMidRangeShot() * 5; // max = 1000
        while(oppositeFoul != 0) {
            int rn = RandomNumber.returnRandomNumber(0, 1000);
            int n = RandomNumber.returnRandomNumber(0, value);
            if(n % 100 == 0){ // 패널티킥
                freeKicker.setShooting(freeKicker.getShooting() + 1);
                freeKicker.setValidShooting(freeKicker.getValidShooting() + 1);
                if(n > 200){
                    freeKicker.setGoal(freeKicker.getGoal() + 1);
                    goalAssistPairs.add(new GoalAssistPairDto(freeKicker.getPlayerId(),null, GoalType.PENALTYKICK));
                }
            }
            else { // 프리킥
                freeKicker.setShooting(freeKicker.getShooting() + 1);
                if (rn < n) {
                    freeKicker.setValidShooting(freeKicker.getValidShooting() + 1);
                }
                if (rn * 2 < n) {
                    freeKicker.setGoal(freeKicker.getGoal() + 1);
                    goalAssistPairs.add(new GoalAssistPairDto(freeKicker.getPlayerId(), null, GoalType.FREEKICK));
                }
            }
            oppositeFoul -=1;
        }
    }


    /**
     *  한명 당
     *  normal 0.065
     *  heading 0.023
     *  mid 0.015
     *
     *  // 포지션에 따라 다름을 부여
     */
    public void updateGoalAndAssist(){
        int pass = passSum / 10;
        for(var ele : playerList){
            int normal = ele.normalShooting(pass / 5);
            while (normal != 0) {
                AutoPersonalData assistant = decisionAssistant();
                assistant.setAssist(assistant.getAssist() + 1);
                goalAssistPairs.add(new GoalAssistPairDto(ele.getPlayerId(),assistant.getPlayerId(), GoalType.NOMAL));
                normal -=1;
            }
            int heading = ele.heading(pass / 10);
            while(heading !=0 ){
                AutoPersonalData assistant = decisionAssistant();
                assistant.setAssist(assistant.getAssist() + 1);
                goalAssistPairs.add(new GoalAssistPairDto(ele.getPlayerId(), assistant.getPlayerId(), GoalType.HEADING));
                heading -=1;
            }
            int mid = ele.midShooting(pass / 10);
            while(mid !=0 ){
                AutoPersonalData assistant = decisionAssistant();
                assistant.setAssist(assistant.getAssist() + 1);
                goalAssistPairs.add(new GoalAssistPairDto(ele.getPlayerId(), assistant.getPlayerId(), GoalType.LONGKICK));
                mid -=1;
            }
        }
    }

    // 공격 기회  -> 확률 -> 코너킥 값을 세팅

    /**
     * 코너킼 한번당
     * shooting = 0.497
     * validationShooting = 0.255
     * goal = 0.164
     */
    public void updateCornerKick(){
        int cornerKick = playerList.stream().mapToInt(ele->ele.getShooting()).sum() ;

        cornerKick /= 5;
        while(cornerKick != 0){
            AutoPersonalData cornerKicker = decisionCornerKicker();
            AutoPersonalData scorer = decisionCornerKickScorer();
            int value = cornerKicker.getStat().getCrosses() + cornerKicker.getStat().getLongPass() + cornerKicker.getStat().getVisualRange();
            value += scorer.getStat().getJump() + scorer.getStat().getHeading() + scorer.getStat().getPositioning();

            int rn = RandomNumber.returnRandomNumber(0,600);
            int n =  RandomNumber.returnRandomNumber(0,value);
            if(rn < n) scorer.setShooting(scorer.getShooting() + 1);
            if(rn * 2 < n) scorer.setValidShooting(scorer.getValidShooting() + 1);
            if(rn * 3 < n) {
                scorer.setGoal(scorer.getGoal() +1);
                cornerKicker.setAssist(cornerKicker.getAssist() + 1);
                goalAssistPairs.add(new GoalAssistPairDto(scorer.getPlayerId(), cornerKicker.getPlayerId(), GoalType.HEADING));
            }

            cornerKick -=1;
        }
    }

    public void updateScore(){
        this.score = this.goalAssistPairs.size();
    }



    private AutoPersonalData decisionFreeKicker(){
        int maxValue = 0;
        AutoPersonalData ret = null;
        for (var ele : playerList){
            Stat stat = ele.getStat();
            int value = stat.getGoalDetermination() * 4 + stat.getMidRangeShot() * 5 + stat.getShootPower() ;
            if(value > maxValue) {
                maxValue = value;
                ret = ele;
            }
        }
        return ret;
    }

    // 어시 기준 솔팅.
    private List<AutoPersonalData> sorting(String type ,List<AutoPersonalData> list){
        List<AutoPersonalData> ret = new ArrayList<>();
        list.stream().forEach(ele->ret.add(ele));
        if(type.equals("Assistant")) {
            ret.sort((e1, e2) -> {
                if (e1.getStat().getActiveness() + e1.getStat().getSpeed() + e1.getStat().getAcceleration() + e1.getStat().getPass()
                        + e1.getStat().getCrosses() + e1.getStat().getLongPass() + e1.getStat().getDribble() > e2.getStat().getActiveness() + e2.getStat().getSpeed()
                        + e2.getStat().getAcceleration() + e2.getStat().getPass() + e2.getStat().getCrosses() + e2.getStat().getLongPass() + e2.getStat().getDribble()) {
                    return 1;
                } else if (e1.getStat().getActiveness() + e1.getStat().getSpeed() + e1.getStat().getAcceleration() + e1.getStat().getPass()
                        + e1.getStat().getCrosses() + e1.getStat().getLongPass() + e1.getStat().getDribble() == e2.getStat().getActiveness() + e2.getStat().getSpeed()
                        + e2.getStat().getAcceleration() + e2.getStat().getPass() + e2.getStat().getCrosses() + e2.getStat().getLongPass() + e2.getStat().getDribble()) {
                    return 0;
                } else return -1;
            });
        }
        else{
            ret.sort((e1, e2) -> {
                if (e1.getStat().getJump() + e1.getStat().getActiveness() + e1.getStat().getBalance() + e1.getStat().getGoalDetermination()
                        + e1.getStat().getPositioning() + e1.getStat().getHeading() + e1.getStat().getVisualRange() > e2.getStat().getJump() + e2.getStat().getActiveness()
                        + e2.getStat().getBalance() + e2.getStat().getGoalDetermination()
                        + e2.getStat().getPositioning() + e2.getStat().getHeading() + e2.getStat().getVisualRange()) {
                    return 1;
                } else if (e1.getStat().getJump() + e1.getStat().getActiveness() + e1.getStat().getBalance() + e1.getStat().getGoalDetermination()
                        + e1.getStat().getPositioning() + e1.getStat().getHeading() + e1.getStat().getVisualRange() == e2.getStat().getJump() + e2.getStat().getActiveness()
                        + e2.getStat().getBalance() + e2.getStat().getGoalDetermination()
                        + e2.getStat().getPositioning() + e2.getStat().getHeading() + e2.getStat().getVisualRange()) {
                    return 0;
                } else return -1;
            });
        }
        return ret;
    }
    private AutoPersonalData decisionCornerKicker(){
        int maxValue = 0;
        AutoPersonalData ret = null;
        for (var ele : playerList){
            Stat stat = ele.getStat();
            int value = stat.getCrosses() * 4 + stat.getPass() * 5 + stat.getLongPass() ;
            if(value > maxValue) {
                maxValue = value;
                ret = ele;
            }
        }
        return ret;
    }
    private AutoPersonalData decisionCornerKickScorer(){
        List<AutoPersonalData> ret = sorting("CornerKicker",playerList);
        // 100 70 60 50 40 30 20 10 8 7
        int rn = RandomNumber.returnRandomNumber(0,410);
        if(rn <= 100) return ret.get(0);
        else if(rn <=170) return ret.get(1);
        else if(rn <=230) return ret.get(2);
        else if(rn <=280) return ret.get(3);
        else if(rn <=320) return ret.get(4);
        else if(rn <=350) return ret.get(5);
        else if(rn <=370) return ret.get(6);
        else if(rn <=390) return ret.get(7);
        else if(rn <=400) return ret.get(8);
        else if(rn <=410) return ret.get(9);
        return null;
    }

    private AutoPersonalData decisionAssistant(){
        List<AutoPersonalData> ret = sorting("Assistant",playerList);
        // 100 70 60 50 40 30 20 10 8 7
        int rn = RandomNumber.returnRandomNumber(0,410);
        if(rn <= 100) return ret.get(0);
        else if(rn <=170) return ret.get(1);
        else if(rn <=230) return ret.get(2);
        else if(rn <=280) return ret.get(3);
        else if(rn <=320) return ret.get(4);
        else if(rn <=350) return ret.get(5);
        else if(rn <=370) return ret.get(6);
        else if(rn <=390) return ret.get(7);
        else if(rn <=400) return ret.get(8);
        else if(rn <=410) return ret.get(9);
        return null;
    }

}
