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
