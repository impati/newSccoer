package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// 평점 리턴 기

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoGradeDecision {
    private final AutoGradeAvgSearchRepository repository;
    private  GradeDto gradeDto;
    /**
     * 경기당 한번만 호출
     * Or
     * 동기화가 필요할 때. 원할 때
     *
     * 단, 오버헤드가 엄청 큼.
     */
    public void callSyn(){
        gradeDto = repository.recordAvg();
    }
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
        value += (data.getPass() / gradeDto.getPass()) * 100; // -> 200
        value += (data.getShooting() / gradeDto.getShooting()) * 50; // -> 100
        value += (data.getValidShooting() / gradeDto.getValidShooting()) * 70; // ->140
        value += (data.getDefense() / gradeDto.getDefense() ) * 30; // ->60
        value /= 10;
        data.setGrade(value);
    }
    private void midFielder(AutoPersonalData data){
        int value = data.getGoal() * 70;
        value += data.getAssist() * 100;
        value += (data.getPass() / gradeDto.getPass()) * 140; // -> 200
        value += (data.getShooting() / gradeDto.getShooting()) * 20; // -> 100
        value += (data.getValidShooting() / gradeDto.getValidShooting()) * 40; // ->140
        value += (data.getDefense() / gradeDto.getDefense() ) * 50; // ->60
        value /= 10;
        data.setGrade(value);
    }
    private void defense(AutoPersonalData data){
        int value = data.getGoal() * 30;
        value += data.getAssist() * 50;
        value += (data.getPass() / gradeDto.getPass()) * 120; // -> 200
        value += (data.getShooting() / gradeDto.getShooting()) * 20; // -> 100
        value += (data.getValidShooting() / gradeDto.getValidShooting()) * 30; // ->140
        value += (data.getDefense() / gradeDto.getDefense() ) * 180; // ->60
        value /= 10;
        data.setGrade(value);
    }
    private void goalKeeper(AutoPersonalData data){
        int value = (int) ((data.getDefense() / gradeDto.getDefense() ) * 400); // ->60
        value /= 10;
        data.setGrade(value);
    }


}
