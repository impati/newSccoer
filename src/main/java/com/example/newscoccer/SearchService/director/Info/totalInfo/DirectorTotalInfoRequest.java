package com.example.newscoccer.SearchService.director.Info.totalInfo;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;


@Data
public class DirectorTotalInfoRequest extends DataTransferObject {
    private Long directorId;
}
