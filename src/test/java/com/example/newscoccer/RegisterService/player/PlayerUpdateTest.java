package com.example.newscoccer.RegisterService.player;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class PlayerUpdateTest {

    @Autowired PlayerUpdate playerUpdate;

    @Autowired
    PlayerRepository playerRepository;
    @PersistenceContext
    EntityManager em;




    @Test
    @DisplayName("선수 등록")
    public void playerRegister() throws Exception{
        // given

        PlayerUpdateDto playerDto = new PlayerUpdateDto();
        playerDto.setName("최준영");
        playerDto.setTeamId(1L);
        playerDto.setPosition(Position.ST);
        playerDto.setAcceleration(5);
        playerDto.setSpeed(5);
        playerDto.setPass(5);
        playerDto.setActiveness(5);
        playerDto.setBalance(5);
        playerDto.setCrosses(5);
        playerDto.setBallControl(5);
        playerDto.setPhysicalFight(5);
        playerDto.setStamina(6);
        playerDto.setJump(6);
        playerDto.setLongPass(6);
        playerDto.setDefense(6);
        playerDto.setDiving(6);
        playerDto.setDribble(6);
        playerDto.setGoalDetermination(6);
        playerDto.setMidRangeShot(6);
        playerDto.setShootPower(6);
        playerDto.setHeading(7);
        playerDto.setTackle(7);
        playerDto.setIntercepting(7);
        playerDto.setSlidingTackle(7);
        playerDto.setHandling(7);
        playerDto.setGoalKick(7);
        playerDto.setSpeedReaction(7);
        playerDto.setPositioning(7);
        playerDto.setVisualRange(7);
        playerDto.setSense(7);

        // when
        Player player = playerUpdate.registerPlayer(playerDto);
        em.flush();
        em.clear();
        // then
        Player findPlayer = playerRepository.findById(player.getId()).get();

        Assertions.assertThat(findPlayer.getName()).isEqualTo("최준영");
        Assertions.assertThat(findPlayer.getTeam().getName()).isEqualTo("바이에른 뮌헨");
        Assertions.assertThat(findPlayer.getPosition()).isEqualTo(Position.ST);
        Assertions.assertThat(findPlayer.getStat().getDribble()).isEqualTo(6);
        Assertions.assertThat(findPlayer.getStat().getSense()).isEqualTo(7);
        Assertions.assertThat(findPlayer.getStat().getPhysicalFight()).isEqualTo(5);
        Assertions.assertThat(findPlayer.getStat().getBalance()).isEqualTo(5);

    }



    @Autowired
    TeamRepository teamRepository;
    @Test
    @DisplayName("선수 수정 기능")
    public void  editPlayer() throws Exception{

        // given
        Team team = teamRepository.findById(1L).orElse(null);
        Player player = Player.createPlayer("testName ",Position.RF,team,new Stat());
        playerRepository.save(player);
        // when
        PlayerUpdateDto playerDto = new PlayerUpdateDto();
        playerDto.setName("최준영");
        playerDto.setTeamId(1L);
        playerDto.setPosition(Position.AM);
        playerDto.setAcceleration(5);
        playerDto.setSpeed(5);
        playerDto.setPass(5);
        playerDto.setActiveness(5);
        playerDto.setBalance(5);
        playerDto.setCrosses(5);
        playerDto.setBallControl(5);
        playerDto.setPhysicalFight(5);
        playerDto.setStamina(6);
        playerDto.setJump(6);
        playerDto.setLongPass(6);
        playerDto.setDefense(6);
        playerDto.setDiving(6);
        playerDto.setDribble(6);
        playerDto.setGoalDetermination(6);
        playerDto.setMidRangeShot(6);
        playerDto.setShootPower(6);
        playerDto.setHeading(7);
        playerDto.setTackle(7);
        playerDto.setIntercepting(7);
        playerDto.setSlidingTackle(7);
        playerDto.setHandling(7);
        playerDto.setGoalKick(7);
        playerDto.setSpeedReaction(7);
        playerDto.setPositioning(7);
        playerDto.setVisualRange(7);
        playerDto.setSense(7);
        playerUpdate.editPlayer(player.getId(), playerDto);

        em.flush();
        em.clear();
        // then

        Player findPlayer = playerRepository.findById(player.getId()).get();

        Assertions.assertThat(findPlayer.getName()).isEqualTo("최준영");
        Assertions.assertThat(findPlayer.getTeam().getName()).isEqualTo("바이에른 뮌헨");
        Assertions.assertThat(findPlayer.getPosition()).isEqualTo(Position.AM);
        Assertions.assertThat(findPlayer.getStat().getDribble()).isEqualTo(6);
        Assertions.assertThat(findPlayer.getStat().getSense()).isEqualTo(7);
        Assertions.assertThat(findPlayer.getStat().getPhysicalFight()).isEqualTo(5);
        Assertions.assertThat(findPlayer.getStat().getBalance()).isEqualTo(5);



    }
}