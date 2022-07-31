package com.example.newscoccer.SearchService.round.common;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundLineUpRequest extends DataTransferObject {
    private Long roundId;
}
