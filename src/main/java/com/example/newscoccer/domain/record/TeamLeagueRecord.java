package com.example.newscoccer.domain.record;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TeamLeagueRecord extends TeamRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "team_league_id")
    private Long id;
}
