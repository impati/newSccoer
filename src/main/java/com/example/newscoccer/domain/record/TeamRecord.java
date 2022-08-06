package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public  class TeamRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_record_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="team_id")
    protected Team team;

    @ManyToOne
    @JoinColumn(name ="director_id")
    protected Director director;


    protected int score ;
    protected int oppositeScore;

    //점유율
    protected int share;
    //코너 킥
    protected int cornerKick;
    //프리킥
    protected int freeKick;


    // 선수들의 기록 합.

    //슈팅
    protected int shooting;
    // 유효 슈팅
    protected int validShooting;

    //파울
    protected int foul;
    //선방 , 세이브
    protected int GoodDefense;
    protected int pass;
    // 팀의 평점 . 선수들의 평균 평점.
    protected double grade;

    @Enumerated(EnumType.STRING)
    protected MatchResult matchResult;



    //later
    protected double rating;

    //현재 시즌 , 라운드일때 또는 n 시즌의 마지막 라운드 일때 유효한 값
    protected int rank;



    public void teamUpdate(int score,int oppositeScore , int share , int cornerKick,int freeKick){
        this.score = score;
        this.oppositeScore = oppositeScore;
        this.share = share;
        this.cornerKick = cornerKick;
        this.freeKick = freeKick;

    }
    // 선수들의 총합기록  -> 매겨변수가 이미 계산되어 넘어옴.
    public void playerRecordSummation(int shooting,int validShooting, int foul,int goodDefense,int pass ,int grade){
        this.setShooting(shooting);
        this.setValidShooting(validShooting);
        this.setFoul(foul);
        this.setGoodDefense(goodDefense);
        this.setPass(pass);
        this.setGrade(grade);
    }



}
