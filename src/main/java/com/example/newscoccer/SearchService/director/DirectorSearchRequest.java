package com.example.newscoccer.SearchService.director;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DirectorSearchRequest extends DataTransferObject {
    private String name;
    private Long leagueId;
    private Long teamId;
}
