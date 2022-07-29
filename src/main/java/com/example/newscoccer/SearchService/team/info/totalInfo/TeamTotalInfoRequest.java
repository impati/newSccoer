package com.example.newscoccer.SearchService.team.info.totalInfo;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamTotalInfoRequest extends DataTransferObject {
    private Long teamId;
}
