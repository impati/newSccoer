package com.example.newscoccer.SearchService.director.Info;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DirectorSimpleInfoResponse extends DataTransferObject {
    private String name;
    private String teamName;
}
