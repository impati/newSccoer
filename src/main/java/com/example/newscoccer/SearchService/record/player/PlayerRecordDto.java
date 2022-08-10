package com.example.newscoccer.SearchService.record.player;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

@Data
public class PlayerRecordDto extends DataTransferObject {
    private String playerName;
    private String teamName;
    private int rank;
    private int goal;
    private int assist;
    private int attackPoint;
    private int shooting;
    private int validShooting;
    private int foul;
    private int pass;
    private int defense;


    public PlayerRecordDto(String playerName, String teamName,
                           int goal, int assist, int shooting,int rank,
                           int validShooting, int foul, int pass, int defense) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.goal = goal;
        this.assist = assist;
        this.shooting = shooting;
        this.validShooting = validShooting;
        this.foul = foul;
        this.pass = pass;
        this.defense = defense;
        this.attackPoint = this.assist + this.goal;
    }
}
