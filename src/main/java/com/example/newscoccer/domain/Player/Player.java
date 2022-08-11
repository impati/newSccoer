package com.example.newscoccer.domain.Player;


import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Team;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Player extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="player_id")
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    private double rating;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name ="team_id")
    private Team team;


    // 현재 팀에서 메인이냐 ? ->true , false
    private boolean main;
    /**
     *  Stat 은 선수에게 종속적 , 생명주기의존 , 선수 Entity 에서만 stat Entity 사용함으로  -> Cascade .All
     *  단 하나이며 선수가 둘이상을 가지는 상황이 존재할 수가 없음.
     */
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name ="stat_id")
    private  Stat stat;


    /***
     * 선수를 생성하는 메서드
     * when : 선수를 생성할 떄 레이팅은 '1500'
     */
    public static Player createPlayer(String name,Position position,Team team,Stat stat) {
        Player player = null;
        if(position == Position.AM || position == Position.CM || position == Position.DM || position == Position.LM || position == Position.RM)
             player = new Midfielder();
        else if(position == Position.GK)
             player = new Goalkeeper();
        else if(position == Position.CF || position == Position.ST || position == Position.RF || position == Position.LF)
             player = new Striker();
        else
             player = new Defender();

        player.setName(name);
        player.setPosition(position);
        player.setRating(1500);
        player.setTeam(team);
        player.setStat(stat);
        return player;
    }

    /**
     * 선수 정보 업데이트
     */
    public void update(String name,Position position ,Team team,
                       int acceleration,int speed,int physicalFight,
                       int stamina,int activeness,int jump,
                       int balance,int ballControl,int crosses,
                       int pass,int longPass,int dribble,
                       int goalDetermination,int midRangeShot,int shootPower,
                       int heading,int defense,int tackle,
                       int intercepting,int slidingTackle,int diving,
                       int handling,int goalKick,int speedReaction,
                       int positioning, int visualRange, int sense
    ) {
        this.setName(name);
        this.setPosition(position);
        this.setTeam(team);
        this.getStat().update(
                acceleration, speed, physicalFight,
                stamina, activeness, jump, balance, ballControl,
                crosses, pass, longPass, dribble, goalDetermination, midRangeShot,
                shootPower, heading, defense,
                tackle, intercepting, slidingTackle, diving,
                handling, goalKick, speedReaction, positioning, visualRange, sense);
    }






}
