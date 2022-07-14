package com.example.newscoccer.RegisterService.director;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class DirectorUpdateTest {

    @Autowired
    private DirectorUpdate directorUpdate;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;
    @Test
    @DisplayName("팀이 원래 있는 감독 팀변경 ")
    void directorChangeTeam(){

        Director director = directorRepository.findById(1L).orElse(null);
        Team team = director.getTeam();

        // 팀이 있는가 .
        assertThat(team).isNotNull();

        // test팀 생성
        League league = new League("testLeague");
        Team changeTeam = Team.createTeam(league,"testTeam");
        teamRepository.save(changeTeam);


        // 팀변경
        directorUpdate.directorEdit(director.getId(),director.getName(),changeTeam.getId());

        assertThat(director.getTeam()).isEqualTo(changeTeam);


    }
    @Test
    @DisplayName("팀이 없었던 감독 팀 변경 ")
    void directorTest(){


        // test director 생성

        Director director = directorUpdate.directorSave("testDirector",null);

        assertThat(director.getTeam()).isNull();



        // test팀 생성
        League league = new League("testLeague");
        Team changeTeam = Team.createTeam(league,"testTeam");
        teamRepository.save(changeTeam);


        directorUpdate.directorEdit(director.getId(),director.getName(),changeTeam.getId());
        assertThat(director.getTeam()).isEqualTo(changeTeam);



    }
    @Test
    @DisplayName("팀이 있었던 감독 -> 팀 없도록 변경")
    void directorSave(){
        // test director 생성

        Director director = directorUpdate.directorSave("testDirector",null);

        League league = new League("testLeague");
        Team changeTeam = Team.createTeam(league,"testTeam");
        teamRepository.save(changeTeam);

        directorUpdate.directorEdit(director.getId(),director.getName(),changeTeam.getId());




        directorUpdate.directorEdit(director.getId(),director.getName(),null);
        assertThat(director.getTeam()).isNull();

    }
    @Test
    @DisplayName("팀 swap ")
    void teamSwap(){

        // when
        League league = new League();
        league.setName("testLeague");
        leagueRepository.save(league);

        Team teamA = Team.createTeam(league,"testA");
        teamRepository.save(teamA);

        Team teamB = Team.createTeam(league,"testB");
        teamRepository.save(teamB);

        Director directorA = Director.createDirector("DirectorA");
        directorA.changeTeam(teamA);

        Director directorB = Director.createDirector("DirectorA");
        directorB.changeTeam(teamB);


        directorRepository.save(directorA);
        directorRepository.save(directorB);

        assertThat(directorA.getTeam()).isEqualTo(teamA);
        assertThat(directorB.getTeam()).isEqualTo(teamB);


        assertThat(teamA.getDirector()).isEqualTo(directorA);
        assertThat(teamB.getDirector()).isEqualTo(directorB);

        // then
        directorUpdate.directorEdit(directorA.getId(),directorA.getName(), teamB.getId());
        directorUpdate.directorEdit(directorB.getId(),directorB.getName(), teamA.getId());

        assertThat(directorA.getTeam()).isEqualTo(teamB);
        assertThat(directorB.getTeam()).isEqualTo(teamA);

        assertThat(teamA.getDirector()).isEqualTo(directorB);
        assertThat(teamB.getDirector()).isEqualTo(directorA);

    }

}
