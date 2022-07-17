package com.example.newscoccer.SearchService.director.Info;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DirectorSimpleInfoRequest extends DataTransferObject {
    private Long directorId;
}
