package com.example.newscoccer.support;

import com.example.newscoccer.domain.Season;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.SeasonRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PostDataTest {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    PostData postData;
    @Autowired
    PlayerRepository playerRepository;

    @PersistenceContext
    EntityManager em;
    @Test
    void initTest() throws IOException {
        postData.init();
        assertThat(seasonRepository.findAll().stream().count()).isEqualTo(1);
        Season season = seasonRepository.findById(1L).orElse(null);
        assertThat(season.getCurrentChampionsRoundSt()).isEqualTo(16);
        assertThat(season.getCurrentSeason()).isEqualTo(0);
        assertThat(season.getCurrentLeagueRoundSt()).isEqualTo(1);
        assertThat(season.getLastLeagueRoundSt()).isEqualTo(45);

        assertThat(leagueRepository.findAll().stream().count()).isEqualTo(4);
        assertThat(teamRepository.findAll().stream().count()).isEqualTo(64);

        teamRepository.findAll().stream().forEach(ele->{
            Long ret = em.createQuery("select count(p) from Player p join p.team t on t = :team",Long.class)
                            .setParameter("team",ele).getSingleResult();
            assertThat(ret).isGreaterThanOrEqualTo(11);
        });
    }



    @Test
    @DisplayName("시즌 두번 생성해보기")
    void test(){
        Season season = new Season();
        Assertions.assertThatThrownBy(()->seasonRepository.save(season)).isInstanceOf(DataIntegrityViolationException.class);
    }


}