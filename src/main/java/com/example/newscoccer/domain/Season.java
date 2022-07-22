package com.example.newscoccer.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

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
    private   int lastLeagueRoundSt = 45; // 리그의 마지막 라운드 TODO: 45수정
    private   int currentLeagueRoundSt = 1; // 현재 리그 라운드를 의미함.
    private   int currentChampionsRoundSt = 16; // 현재 챔피언스 리그 라운드를 의미함,
}
