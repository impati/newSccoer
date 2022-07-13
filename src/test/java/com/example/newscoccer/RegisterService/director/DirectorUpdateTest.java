package com.example.newscoccer.RegisterService.director;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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

}
