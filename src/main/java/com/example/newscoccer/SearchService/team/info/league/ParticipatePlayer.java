package com.example.newscoccer.SearchService.team.info.league;


import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipatePlayer{
    /**
     * 선수의 현재 정보들/
     */
    private Long playerId;
    private String playerName;
    private Position position;
    private double rating;

    private int game; // 시즌단위 , 선수가 참가한 경기수
}