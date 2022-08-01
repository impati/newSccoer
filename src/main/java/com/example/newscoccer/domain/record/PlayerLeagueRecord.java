package com.example.newscoccer.domain.record;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PlayerLeagueRecord extends PlayerRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="player_league_id")
    private Long id;



    public void update(int goal, int assist,int pass,
                       int shooting ,int validShooting,int foul,
                       int defense , int grade){
        this.goal = goal;
        this.assist = assist;
        this.pass = pass;
        this.shooting = shooting;
        this.validShooting = validShooting;
        this.foul= foul;
        this.goodDefense = defense;
        this.grade = grade;

    }


}
