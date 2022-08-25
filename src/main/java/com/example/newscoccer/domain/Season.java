package com.example.newscoccer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public  class Season {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    private Long id;
    @Column(unique = true)
    private   int currentSeason = 0; // 시즌 시작할때 하나씩 더해야함.
    private   int lastLeagueRoundSt = 45; // 리그의 마지막 라운드
    private   int currentLeagueRoundSt = 1; // 현재 리그 라운드를 의미함.
    private   int currentChampionsRoundSt = 16; // 현재 챔피언스 리그 라운드를 의미함,



    public void leagueUpdate(){
        if(currentLeagueRoundSt != lastLeagueRoundSt) currentLeagueRoundSt++;
        SeasonUtilsUpdate();
    }

    public void championsUpdate(){
        currentChampionsRoundSt /= 2;
        SeasonUtilsUpdate();
    }
    public void seasonUpdate(){
        currentSeason += 1;
        currentLeagueRoundSt = 1;
        currentChampionsRoundSt = 16;
        SeasonUtilsUpdate();
    }


    // 내부적으로 Season 변경시 항상 호출해줘야함.
    public void SeasonUtilsUpdate(){
        SeasonUtils.currentSeason = currentSeason;
        SeasonUtils.currentLeagueRoundSt = currentLeagueRoundSt;
        SeasonUtils.lastLeagueRoundSt = lastLeagueRoundSt;
        SeasonUtils.currentChampionsRoundSt = currentChampionsRoundSt;
    }

}
