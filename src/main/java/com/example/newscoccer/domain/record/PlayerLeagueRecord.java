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
}
