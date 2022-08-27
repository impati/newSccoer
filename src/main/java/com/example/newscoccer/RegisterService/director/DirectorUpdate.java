package com.example.newscoccer.RegisterService.director;

import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 감독의 저장 , 수정 기능을 변경할 일이 있을까 ? -> 바로 클래스로 만듦.
 *
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DirectorUpdate {
    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;
    /**
     *
     *  감독에 팀이 없는 경우 team = null로 설정 ,  따라서 Id = null 로 주입해야함
     *
     *
     *
     */

    /**
     * 감독 저장.
     */
    public Director directorSave(String name , Long teamId){
        Director director = Director.createDirector(name);
        if(teamId != null) {
            Team team = teamRepository.findById(teamId).orElse(null);
            director.changeTeam(team);
        }
        directorRepository.save(director);
        return director;
    }

    /**
     * 감독 수정 .
     */
    public void directorEdit(Long directorId , String name , Long changeTeamId){
        Director director = directorRepository.findById(directorId).orElse(null);
        director.setName(name);
        if(changeTeamId != null) {
            Team team = teamRepository.findById(changeTeamId).orElse(null);
            director.changeTeam(team);
        }
        else director.changeTeam(null);

    }


}
