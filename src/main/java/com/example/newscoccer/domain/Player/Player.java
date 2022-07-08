package com.example.newscoccer.domain.Player;


import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public static Player createPlayer(String name,Position position,Team team,Stat stat){
        Player player = new Player();
        player.setName(name);
        player.setPosition(position);
        player.setRating(1500);
        player.setTeam(team);
        player.setStat(stat);
        return player;
    }

}
