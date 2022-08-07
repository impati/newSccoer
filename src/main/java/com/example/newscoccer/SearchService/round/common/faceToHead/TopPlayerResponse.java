package com.example.newscoccer.SearchService.round.common.faceToHead;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TopPlayerResponse extends DataTransferObject {
    private List<TopPlayerDto> teamAPlayerList = new ArrayList<>();
    private List<TopPlayerDto> teamBPlayerList = new ArrayList<>();
}
