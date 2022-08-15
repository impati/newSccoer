package com.example.newscoccer.auto.Game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AutoGradeAvgSearchRepository {
    private final EntityManager em;
    public GradeDto recordAvg(){
        return em.createQuery("select " +
                " new com.example.newscoccer.auto.Game.GradeDto(avg(pr.pass),avg(pr.shooting),avg(pr.validShooting),avg(pr.foul),avg(pr.goodDefense)) " +
                " from PlayerRecord pr " +
                " where pr.grade <> 0 ",GradeDto.class).getSingleResult();
    }

}
