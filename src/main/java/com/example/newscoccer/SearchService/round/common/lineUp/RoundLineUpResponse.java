package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  팀A < 팀B (ID)
 */
@Data
public class RoundLineUpResponse extends DataTransferObject {
    private String teamAName;
    private Long teamAId;
    private Long teamBId;
    private String teamBName;
    private List<RoundLineUpDto> playerListA = new ArrayList<>();
    private List<RoundLineUpDto> playerListB = new ArrayList<>();
    private boolean lineUpDone = false;


    public void teamAUpdate(Team team){
        this.teamAId = team.getId();
        this.teamAName = team.getName();
    }

    public void teamBUpdate(Team team){
        this.teamBId = team.getId();
        this.teamBName = team.getName();
    }


}
