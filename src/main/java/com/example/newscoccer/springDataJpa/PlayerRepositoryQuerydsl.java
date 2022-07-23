package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.SearchService.player.search.PlayerSearchRequest;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;

import java.util.List;

public interface PlayerRepositoryQuerydsl {
    List<Player> playerSearch (String name , Long leagueId,Long teamId , List<Position> positions);
}
