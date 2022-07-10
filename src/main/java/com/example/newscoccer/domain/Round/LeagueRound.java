package com.example.newscoccer.domain.Round;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("League")
@NoArgsConstructor
public class LeagueRound extends Round{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "league_round_id")
    private Long id;

    public LeagueRound(int season, int roundSt) {
        super(season, roundSt);
    }
}
