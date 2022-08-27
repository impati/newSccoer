package com.example.newscoccer.SearchService.director.Info;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DirectorSimpleInfoResponse extends DataTransferObject {
    private String name;
    private String teamName;
}
