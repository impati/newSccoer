package com.example.newscoccer.domain.Round;

import com.example.newscoccer.domain.League;
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
    @Column(name= "round_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="league_id")
    private League league;

    public LeagueRound(League league , int season, int roundSt) {
        this.season = season;
        this.roundSt = roundSt;
        this.league = league;
        roundStatus = RoundStatus.YET;
    }
}
