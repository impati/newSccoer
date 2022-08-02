package com.example.newscoccer.SearchService.round.championsRound;

import com.example.newscoccer.SearchService.round.RoundInfoDto;
import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChampionsRoundInfoResponse extends DataTransferObject {
    List<RoundInfoDto> firstRound = new ArrayList<>();
    List<RoundInfoDto> secondRound = new ArrayList<>();
}
