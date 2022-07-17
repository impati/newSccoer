package com.example.newscoccer.SearchService.director.league;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DirectorLeagueInfoRequest extends DataTransferObject {
    private Long directorId;
    private int season;
}
