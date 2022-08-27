package com.example.newscoccer.SearchService.director.search;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DefaultDirectorSearch implements DirectorSearch{
    private final DirectorRepository directorRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    /**
     *  이름 , 리그 , 팀 정보를 통해 감독 서치 기능 .
     */
    @Override
    public List<DirectorSearchResponse> directorSearch(DirectorSearchRequest request) {
        League league = null;
        Team team = null;
        if(request.getLeagueId() != null) league = leagueRepository.findById(request.getLeagueId()).orElse(null);
        if(request.getTeamId() != null) team = teamRepository.findById(request.getTeamId()).orElse(null);
        return directorRepository.findDirectorList(request.getName(),league,team)
                .stream().map(ele->new DirectorSearchResponse(ele.getId(),ele.getName(),ele.getTeam())).collect(Collectors.toList());

    }

}
