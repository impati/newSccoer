package com.example.newscoccer.SearchService.record.team;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchTeamRecordResponse extends DataTransferObject {
    List<TeamRecordDto> resultList = new ArrayList<>();
}
