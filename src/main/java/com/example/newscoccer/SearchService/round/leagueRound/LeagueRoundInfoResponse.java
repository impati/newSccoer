package com.example.newscoccer.SearchService.round.leagueRound;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.SearchService.round.RoundInfoDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LeagueRoundInfoResponse extends DataTransferObject {
    List<RoundInfoDto> roundInfoDtoList = new ArrayList<>();
}
