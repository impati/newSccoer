package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 경기 결과에 대한 선수 기록
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResultPlayerDto extends DataTransferObject {
    private Long playerId;
    private String playerName;
    private Position position;

    private int goal;
    private int assist;

    private int pass;

    private int shooting;
    private int validShooting;

    private int foul;
    private int defense;

    private int grade;

    public GameResultPlayerDto(Long playerId, String playerName, Position position) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.position = position;
    }

    public void dataSetting(int goal,int assist,int pass,
                            int shooting,int validShooting,
                            int foul,int defense,int grade){

        this.goal = goal;
        this.assist = assist;
        this.pass = pass;
        this.shooting = shooting;
        this.validShooting = validShooting;
        this.foul = foul;
        this.defense = defense;
        this.grade = grade;
    }




}
