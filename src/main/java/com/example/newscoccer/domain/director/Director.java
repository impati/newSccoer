package com.example.newscoccer.domain.director;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 *  감독은 맡은 팀이 없을 수도 있다.
 *  감독이 맡은 팀이 바뀌는 시점은 시즌이 끝나고 나서다. (반드시 한 시즌을 마무리 해야함)
 *  감독 기록은 팀의 기록에 의해 기록됨.
 */
@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="director_id")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="team_Id")
    private Team team;
    // 연관관계 편의 메서드
    public void changeTeam(Team team){
        if(team == null){
            this.setTeam(null);
        }
        else {
            team.setDirector(this);
            this.setTeam(team);
        }

    }
    public static Director createDirector(String name) {
        Director director = new Director();
        director.setName(name);
        return director;
    }
}
