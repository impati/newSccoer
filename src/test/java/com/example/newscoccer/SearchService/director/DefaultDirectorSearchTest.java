package com.example.newscoccer.SearchService.director;

import com.example.newscoccer.RegisterService.director.DirectorUpdate;
import com.example.newscoccer.SearchService.director.search.DirectorSearch;
import com.example.newscoccer.SearchService.director.search.DirectorSearchRequest;
import com.example.newscoccer.SearchService.director.search.DirectorSearchResponse;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class DefaultDirectorSearchTest {

    @Autowired
    DirectorSearch directorSearch;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    LeagueRepository  leagueRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    DirectorUpdate directorUpdate;
    @Test
    @DisplayName("감독 서치 기능 with 동적 쿼리 이름만 ")
    void directorSearchNameOnly(){
        List<DirectorSearchResponse> resp = directorSearch.directorSearch(new DirectorSearchRequest("스", null, null));
        resp.stream().forEach(element->{
            Assertions.assertThat(element.getDirectorName().contains("스")).isTrue();
        });
        System.out.println(resp.size());

    }

    @Test
    @DisplayName("감독 서치 기능 with 리그만  ")
    void directorSearchLeagueOnly(){
        List<DirectorSearchResponse> directorList = directorSearch.directorSearch(new DirectorSearchRequest(null, 1L, null));
        Assertions.assertThat(directorList.size()).isEqualTo(16);
    }

    @Test
    @DisplayName("감독 서치 기능 with 리그 , 이름 ")
    void directorSearchLeagueOnlyAndName(){
        List<DirectorSearchResponse> directorList = directorSearch.directorSearch(new DirectorSearchRequest("스" ,1L, null));
        directorList.stream().forEach(element->{
            Assertions.assertThat(element.getDirectorName().contains("스")).isTrue();
        });

        System.out.println(directorList.size());
    }
    @Test
    @DisplayName("감독 서치 기능 with 리그 , 이름 , 팀 ")
    void directorSearchLeagueOnlyAndNameAndTeam(){

        League league = new League("test");
        leagueRepository.save(league);

        Team team = Team.createTeam(league,"testTeam");
        teamRepository.save(team);


        Team findTeam = teamRepository.findByName("testTeam").get();

        directorUpdate.directorSave("testDirector",findTeam.getId());


        List<DirectorSearchResponse> directorList = directorSearch.directorSearch(new DirectorSearchRequest("testDirector", league.getId(), findTeam.getId()));
        Assertions.assertThat(directorList.size()).isEqualTo(1);
        Assertions.assertThat(directorList.get(0).getDirectorName()).isEqualTo("testDirector");

        Assertions.assertThat(directorList.get(0).getDirectorName()).isEqualTo("testDirector");
    }

}