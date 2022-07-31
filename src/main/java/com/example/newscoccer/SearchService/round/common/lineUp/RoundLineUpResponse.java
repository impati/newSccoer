package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  팀A < 팀B (ID)
 */
@Data
public class RoundLineUpResponse extends DataTransferObject {

    private String teamAName;
    private String teamBName;
    private List<RoundLineUpDto> playerListA = new ArrayList<>();
    private List<RoundLineUpDto> playerListB = new ArrayList<>();

}
