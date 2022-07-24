package com.example.newscoccer.SearchService.director.Info.champions;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DirectorChampionsInfoRequest extends DataTransferObject {
    private Long directorId;
    private int season;
}
