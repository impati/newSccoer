package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComparisonRecordResponse extends DataTransferObject {
    List<ComparisonRecordDto> recordList = new ArrayList<>();
}
