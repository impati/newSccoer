package com.example.newscoccer.springDataJpa.dto;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Team;
import lombok.Data;

@Data
public class TeamScoreDto extends DataTransferObject {
    private Team team;
    private int score;

    public TeamScoreDto(Team team, Long score) {
        this.team = team;
        this.score = Math.toIntExact(score);
    }
}
