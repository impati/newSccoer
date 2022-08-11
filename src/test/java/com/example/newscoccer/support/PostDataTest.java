package com.example.newscoccer.support;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Season;
import com.example.newscoccer.domain.Team;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

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






    @Test
    @DisplayName("최소 수비수 ,최소 공격수 , 최소 미드필더 수 구하기 ")
    public void mini() throws Exception{
        // given

        List<Team> teamList = teamRepository.findAll();
        int minDefense = 987654321;
        int minAttacker = 98765421;
        int minMid = 987654321;

        // when
        for(var t : teamList){
            List<Player> playerList = playerRepository.findByTeam(t);
            int d = 0, a = 0, m = 0;
            for(var p : playerList){
                if(p.getPosition() == Position.ST || p.getPosition() == Position.CF ||
                p.getPosition() == Position.RF || p.getPosition() == Position.LF){
                    a++;
                }
                else if(p.getPosition() == Position.AM || p.getPosition() == Position.LM ||
                p.getPosition() == Position.CM || p.getPosition() == Position.RM ||
                p.getPosition() == Position.DM ){
                    m++;
                }
                else if(p.getPosition() == Position.LB || p.getPosition() == Position.LWB ||
                p.getPosition() == Position.CB || p.getPosition() == Position.RB ||
                p.getPosition() == Position.RWB) {
                    d++;
                }
            }
            minDefense = Math.min(minDefense,d);
            minAttacker = Math.min(minAttacker,a);
            minMid = Math.min(minMid,m);
        }
        // then

        System.out.println("MinDefense = " + minDefense); // 3
        System.out.println("MinAttack = " + minAttacker); // 3
        System.out.println("MinMid = " + minMid); // 6
    }



    @Test
    @DisplayName("선발 선수 테스트")
    public void playerMainTest() throws Exception{
        // given
        List<Team> teamList = teamRepository.findAll();
        // when
        for(var t : teamList){
            List<Player> playerList = playerRepository.findByTeam(t);
            int d = 0, a = 0, m = 0,g=0;
            for(var p : playerList){
                if(p.isMain()) {
                    if (p.getPosition() == Position.ST || p.getPosition() == Position.CF ||
                            p.getPosition() == Position.RF || p.getPosition() == Position.LF) {
                        a++;
                    } else if (p.getPosition() == Position.AM || p.getPosition() == Position.LM ||
                            p.getPosition() == Position.CM || p.getPosition() == Position.RM ||
                            p.getPosition() == Position.DM) {
                        m++;
                    } else if (p.getPosition() == Position.LB || p.getPosition() == Position.LWB ||
                            p.getPosition() == Position.CB || p.getPosition() == Position.RB ||
                            p.getPosition() == Position.RWB) {
                        d++;
                    } else
                        g++;
                }
            }
           Assertions.assertThat(d).isEqualTo(4);
           Assertions.assertThat(a).isEqualTo(3);
           Assertions.assertThat(m).isEqualTo(3);
           Assertions.assertThat(g).isEqualTo(1);
        }


        // then

    }















}