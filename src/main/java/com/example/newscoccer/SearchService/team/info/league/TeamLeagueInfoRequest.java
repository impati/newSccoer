package com.example.newscoccer.SearchService.team.info.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamLeagueInfoRequest extends DataTransferObject {
    private Long teamId;
    private int season;
}
