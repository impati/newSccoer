package com.example.newscoccer.SearchService.player.search;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultPlayerSearch implements PlayerSearch{
    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    @Override
    public List<PlayerSearchResponse> playerSearch(PlayerSearchRequest req) {
        consistency(req);
        return playerRepository.playerSearch(req.getPlayerName(),req.getLeagueId(),
                    req.getTeamId(),req.getPositions())
                .stream()
                .map(player->new PlayerSearchResponse(
                        player.getId(),player.getName(),
                        player.getTeam().getName(),player.getPosition()))
                .collect(Collectors.toList());
    }

    /**
     * 리그 , 팀 정보가 맞지 않을 시 팀정보는 무시되며 리그 정보로 검색됨.
     * @param req
     */
    private void consistency(PlayerSearchRequest req) {
        if(req.getLeagueId() != null){
            League league = leagueRepository.findById(req.getLeagueId()).orElse(null);
            if(req.getTeamId() != null){
                Team team = teamRepository.findById(req.getTeamId()).orElse(null);
                if(!team.getLeague().equals(league)) req.setTeamId(null); 
            }
        }
    }


}

