package com.example.newscoccer.SearchService.round.championsRound;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 챔피언스 라운드를 가져오기위한 request
 *
 */
@Data
@AllArgsConstructor
public class ChampionsRoundInfoRequest extends DataTransferObject {
    private int season;
    private int roundSt;
}
