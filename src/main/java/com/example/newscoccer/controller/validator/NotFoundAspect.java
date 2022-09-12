package com.example.newscoccer.controller.validator;

import com.example.newscoccer.controller.*;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class NotFoundAspect {


    private final RoundRepository roundRepository;
    private final DirectorRepository directorRepository;
    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    /**
     *  arg 는 항상 Long 타입의 pk임 .
     *
     */
    @Before("@annotation(com.example.newscoccer.controller.validator.NotFoundValidation) && args(id,..)")
    public void notFoundEntity(JoinPoint joinPoint,Long id){
        if(joinPoint.getTarget() instanceof RoundController) exception(roundRepository.findById(id).orElse(null));
        if(joinPoint.getTarget() instanceof DirectorController) exception(directorRepository.findById(id).orElse(null));
        if(joinPoint.getTarget() instanceof PlayerController )exception(playerRepository.findById(id).orElse(null));
        if(joinPoint.getTarget() instanceof LeagueController) exception(leagueRepository.findById(id).orElse(null));
        if(joinPoint.getTarget() instanceof TeamController) exception(teamRepository.findById(id).orElse(null));
        if(joinPoint.getTarget() instanceof RecordController){
            if(id != null)
                exception(leagueRepository.findById(id).orElse(null));
        }




    }
    private void exception(Object obj){if(obj == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
}
