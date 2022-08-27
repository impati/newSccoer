package com.example.newscoccer.SearchService.director.Info.totalInfo;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DirectorTotalInfoRequest extends DataTransferObject {
    private Long directorId;
}
