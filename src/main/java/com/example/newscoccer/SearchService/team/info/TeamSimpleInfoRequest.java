package com.example.newscoccer.SearchService.team.info;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamSimpleInfoRequest extends DataTransferObject {
    private Long teamId;
}
