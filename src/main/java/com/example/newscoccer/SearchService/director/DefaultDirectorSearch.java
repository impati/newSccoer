package com.example.newscoccer.SearchService.director;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DefaultDirectorSearch implements DirectorSearch{
    private final DirectorRepository directorRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    @Override
    public List<Director> directorSearch(DirectorSearchRequest request) {
        League league = null;
        Team team = null;
        if(request.getLeagueId() != null) league = leagueRepository.findById(request.getLeagueId()).orElse(null);
        if(request.getTeamId() != null) team = teamRepository.findById(request.getTeamId()).orElse(null);
        return directorRepository.findDirectorList(request.getName(),league,team);

    }

}
