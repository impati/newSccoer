package com.example.newscoccer.SearchService.player.search;


import com.example.newscoccer.domain.Player.Player;

import java.util.List;

public interface PlayerSearch {
    List<PlayerSearchResponse> playerSearch(PlayerSearchRequest req);
}
