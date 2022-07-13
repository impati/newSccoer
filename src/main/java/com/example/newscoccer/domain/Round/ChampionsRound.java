package com.example.newscoccer.domain.Round;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Champions")
public class ChampionsRound extends Round{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "champions_round_id")
    private Long id;

    private int index;

    public ChampionsRound(int season, int roundSt, int index) {
        super(season, roundSt);
        this.index = index;
    }
}