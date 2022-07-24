package com.example.newscoccer.SearchService.player.info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultPlayerSimpleInfoTest {

    @Autowired
    EntitySimpleInfo<PlayerSimpleInfoRequest , PlayerSimpleInfoResponse> entitySimpleInfo;

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Test
    @DisplayName("선수 기본 정보 테스트")
    public void simpleInfo() throws Exception{
        // given
        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();
        Player player = maker.getPlayer();

        // when
        Assertions.assertThat(player.getId()).isNotNull();

        PlayerSimpleInfoResponse resp = entitySimpleInfo.simpleInfo(new PlayerSimpleInfoRequest(player.getId()));

        // then 

        Assertions.assertThat(resp.getPlayerName()).isEqualTo("testPlayer");
        Assertions.assertThat(resp.getTeamName()).isEqualTo("testTeam");
        Assertions.assertThat(resp.getPosition()).isEqualTo(Position.ST);
        Assertions.assertThat(resp.getCurrentRating()).isEqualTo(1500);

    }
}