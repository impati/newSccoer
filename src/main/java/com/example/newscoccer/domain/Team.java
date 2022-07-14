package com.example.newscoccer.domain;

import com.example.newscoccer.domain.director.Director;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 *  -  팀은 감독이 항상 있다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="team_id")
    private Long id;
    private String name;
    private double rating;

    @ManyToOne
    @JoinColumn(name ="league_id")
    private League league;

    @OneToOne(mappedBy = "team")
    private Director director;

    public static Team createTeam(League league,String name){
        Team team = new Team();
        team.setLeague(league);
        team.setName(name);
        team.setRating(1500);
        return team;
    }
}
