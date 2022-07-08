package com.example.newscoccer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public  class Season {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    private Long id;
    public  int currentSeason = 0; // 시즌 시작할때 하나씩 더해야함.
    public  int LastLeagueRoundSt = 15; // 리그의 마지막 라운드 TODO: 45수정
    public  int CurrentLeagueRoundSt = 1; // 현재 리그 라운드를 의미함.
    public  int CurrentChampionsRoundSt = 16; // 현재 챔피언스 리그 라운드를 의미함,
}
