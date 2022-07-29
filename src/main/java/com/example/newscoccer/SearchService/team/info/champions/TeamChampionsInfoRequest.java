package com.example.newscoccer.SearchService.team.info.champions;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamChampionsInfoRequest extends DataTransferObject {
    private Long teamId;
    private int season;
}
