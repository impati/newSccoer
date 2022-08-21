package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// 평점 리턴기

@Slf4j
@Component
@Transactional
public class AutoGradeDecision {
    public void gradeDecision(AutoPersonalData data){
        //  gradeDto 값이 존재해야함.
        if (data.getParticipatePosition() == Position.ST || data.getParticipatePosition() == Position.CF ||
                data.getParticipatePosition() == Position.RF || data.getParticipatePosition() == Position.LF) {
            striker(data);
        } else if (data.getParticipatePosition() == Position.AM || data.getParticipatePosition() == Position.LM ||
                data.getParticipatePosition() == Position.CM ||data.getParticipatePosition() == Position.RM ||
                data.getParticipatePosition() == Position.DM) {
            midFielder(data);
        } else if (data.getParticipatePosition() == Position.GK){
            goalKeeper(data);
        }
        else {
            defense(data);
        }
    }


    private void striker(AutoPersonalData data){
        // 100점 만점
        int value = data.getGoal() * 100;
        value += data.getAssist() * 70;
        value += (data.getPass() / 26.6) * 400; // -> 200
        value += (data.getShooting() / 1.6) * 200; // -> 100
        value += (data.getValidShooting() / 1.1) * 300; // ->140
        value += (data.getDefense() / 13.4 ) * 100; // ->60
        value /= 20;
        data.setGrade(value);
    }
    private void midFielder(AutoPersonalData data){
        int value = data.getGoal() * 70;
        value += data.getAssist() * 100;
        value += (data.getPass() / 49.5) * 400; // -> 200
        value += (data.getShooting() / 1.4) * 150; // -> 100
        value += (data.getValidShooting() / 1) * 100; // ->140
        value += (data.getDefense() / 31.3 ) * 350; // ->60
        value /= 20;
        data.setGrade(value);
    }
    private void defense(AutoPersonalData data){
        int value = data.getGoal() * 30;
        value += data.getAssist() * 50;
        value += (data.getPass() / 45) * 500; // -> 200
        value += (data.getShooting() / 0.2) * 50; // -> 100
        value += (data.getValidShooting() / 0.15) * 50; // ->140
        value += (data.getDefense() / 39.5 ) * 500; // ->60
        value /= 12;
        data.setGrade(value);
    }
    private void goalKeeper(AutoPersonalData data){
        int value = (data.getDefense() / 7 ) * 1000;
        value += (data.getPass() / 7) *100;
        value /= 6;

        data.setGrade(value);
    }


}

/**
 *
 *
 *
 *
 standard

 Position =ST
 goal = 1.104 assist = 0.974 defense = 13.372 pass = 27.804 Shooting = 4.266 validShooting = 2.108 foul = 1.742 grade = 89.092
 =================================
 Position =LF
 goal = 0.684 assist = 0.46 defense = 12.944 pass = 26.884 Shooting = 1.564 validShooting = 1.072 foul = 1.734 grade = 53.304
 =================================
 Position =RF
 goal = 0.642 assist = 0.522 defense = 13.384 pass = 27.376 Shooting = 1.62 validShooting = 1.114 foul = 1.726 grade = 54.802


 =================================
 Position =AM
 goal = 0.542 assist = 0.23 defense = 32.348 pass = 48.636 Shooting = 1.272 validShooting = 0.906 foul = 1.858 grade = 51.062
 =================================
 Position =DM
 goal = 0.576 assist = 0.182 defense = 31.588 pass = 48.326 Shooting = 1.374 validShooting = 0.976 foul = 1.722 grade = 51.3
 =================================
 Position =LM
 goal = 0.59 assist = 0.26 defense = 31.958 pass = 47.828 Shooting = 1.414 validShooting = 0.992 foul = 1.668 grade = 52.016
 =================================
 Position =RM
 goal = 0.564 assist = 0.416 defense = 31.53 pass = 48.784 Shooting = 1.408 validShooting = 0.98 foul = 1.626 grade = 52.808
 =================================




 Position =LB
 goal = 0.082 assist = 0.074 defense = 40.164 pass = 45.18 Shooting = 0.288 validShooting = 0.14 foul = 1.574 grade = 56.542
 =================================
 Position =RB
 goal = 0.092 assist = 0.076 defense = 39.684 pass = 45.084 Shooting = 0.264 validShooting = 0.156 foul = 1.644 grade = 55.374
 =================================
 Position =CB
 goal = 0.108 assist = 0.168 defense = 38.298 pass = 44.862 Shooting = 0.282 validShooting = 0.168 foul = 1.668 grade = 54.912


 =================================
 Position =GK
 goal = 0.01 assist = 0.0 defense = 7.298 pass = 7.296 Shooting = 0.014 validShooting = 0.01 foul = 0.002 grade = 47.858



 *
 *
 */
