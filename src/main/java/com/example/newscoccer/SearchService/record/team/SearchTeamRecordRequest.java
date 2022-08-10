package com.example.newscoccer.SearchService.record.team;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchTeamRecordRequest extends DataTransferObject {
    private int season;
    private Long leagueId;
}
