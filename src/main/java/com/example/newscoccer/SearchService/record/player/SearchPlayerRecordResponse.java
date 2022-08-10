package com.example.newscoccer.SearchService.record.player;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class SearchPlayerRecordResponse {
    private List<PlayerRecordDto> resultList = new ArrayList<>();
}
