package com.example.newscoccer.SearchService.player.search;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Position;
import lombok.Data;
import org.hibernate.collection.internal.PersistentList;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerSearchRequest extends DataTransferObject {
    private String playerName;
    private Long leagueId ;
    private Long teamId;
    private List<Position> positions = new ArrayList<>();

}
