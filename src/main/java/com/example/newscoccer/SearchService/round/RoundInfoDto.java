package com.example.newscoccer.SearchService.round;

import com.example.newscoccer.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * - 팀A vs 팀B ( 항상 팀id 가 작은 팀이  A)
 * - score
 * - 경기가 끝났는지 여부
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundInfoDto {
    private Long roundId;
    private Team teamA;
    private Team teamB;

    private int scoreA;
    private int scoreB;

    private boolean isDone;

}
