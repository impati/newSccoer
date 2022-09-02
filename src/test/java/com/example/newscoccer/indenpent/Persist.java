package com.example.newscoccer.indenpent;

import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class Persist {
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("")
    public void roundTest() throws Exception{
        Round round = new LeagueRound();
        em.persist(round);



        em.flush();
        em.clear();


        Round findRound1 = em.find(LeagueRound.class,round.getId());


        System.out.println("findRound1 = " + findRound1);


        em.flush();
        em.clear();

        Round findRound2 = em.find(LeagueRound.class,round.getId());


        System.out.println("findRound1 = " + findRound2);








    }
}
