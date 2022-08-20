package com.example.newscoccer.auto.Game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AutoGradeAvgSearchRepository {
    private final EntityManager em;
    public Optional<GradeDto> recordAvg(){
        return Optional.ofNullable(em.createQuery("select " +
                " new com.example.newscoccer.auto.Game.GradeDto(avg(pr.pass),avg(pr.shooting),avg(pr.validShooting),avg(pr.foul),avg(pr.goodDefense)) " +
                " from PlayerRecord pr " +
                " where pr.grade <> 0 ",GradeDto.class).getSingleResult());
    }

}
