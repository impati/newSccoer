package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  경기 결과에 대한 팀의 기록
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResultTeamDto extends DataTransferObject {
    private Long teamId;//
    private String teamName;//
    private int score;//
    private double share;//
    private int cornerKick;//
    private int freeKick;//

    public GameResultTeamDto(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
