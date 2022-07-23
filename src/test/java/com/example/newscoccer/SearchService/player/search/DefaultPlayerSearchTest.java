package com.example.newscoccer.SearchService.player.search;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DefaultPlayerSearchTest {

    @Autowired
    PlayerSearch playerSearch;
    @Autowired
    PlayerRepository playerRepository;

    @Test
    @DisplayName("선수 검색 동적 쿼리 - 선수 이름으로만 검색")
    public void playerSearch() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setPlayerName("스");
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then

        System.out.println("size = " + resp.size());
        for (PlayerSearchResponse playerSearchResponse : resp) {
            System.out.println("name = " + playerSearchResponse.getPlayerName());
            org.assertj.core.api.Assertions.assertThat(playerSearchResponse.getPlayerName()).contains("스");
        }
    }

    @Test
    @DisplayName("선수 검색 동적 쿼리 -  리그 ")
    public void  playerLeague() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setLeagueId(1L);
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        org.assertj.core.api.Assertions.assertThat(resp.size()).isGreaterThanOrEqualTo(11 * 16);
        for (PlayerSearchResponse playerSearchResponse : resp) {
            Player player = playerRepository.findById(playerSearchResponse.getPlayerId()).get();
            Team team = player.getTeam();
            org.assertj.core.api.Assertions.assertThat(team.getLeague().getId()).isEqualTo(1);
        }
    }

    @Test
    @DisplayName("선수 검색 동적 쿼리 -  선수 + 리그")
    public void playerNameAndLeague() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setLeagueId(1L);
        req.setPlayerName("스");
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);

        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {
            org.assertj.core.api.Assertions.assertThat(playerSearchResponse.getPlayerName()).contains("스");
            Player player = playerRepository.findById(playerSearchResponse.getPlayerId()).get();
            Team team = player.getTeam();
            org.assertj.core.api.Assertions.assertThat(team.getLeague().getId()).isEqualTo(1);
        }
    }

    @Test
    @DisplayName("선수 검색 동적 쿼리 -   리그 + 팀")
    public void teamAndLeague() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setLeagueId(1L);
        req.setTeamId(1L);
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {

            Player player = playerRepository.findById(playerSearchResponse.getPlayerId()).get();
            Team team = player.getTeam();
            org.assertj.core.api.Assertions.assertThat(team.getId()).isEqualTo(1L);
            org.assertj.core.api.Assertions.assertThat(team.getLeague().getId()).isEqualTo(1);
        }

    }


    @Test
    @DisplayName("선수 검색 동적 쿼리 -   리그 + 팀 매치 x")
    public void teamAndLeagueNotMatch() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setLeagueId(2L);
        req.setTeamId(1L);
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {
            Player player = playerRepository.findById(playerSearchResponse.getPlayerId()).get();
            Team team = player.getTeam();
            org.assertj.core.api.Assertions.assertThat(team.getLeague().getId()).isEqualTo(2);
        }
    }

    @Test
    @DisplayName("선수 검색 동적 쿼리 -  포지션")
    public void position() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.getPositions().add(Position.AM);
        req.getPositions().add(Position.RM);
        req.getPositions().add(Position.CM);

        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {
            Position position = playerSearchResponse.getPosition();
            System.out.println("position = " + position);
            if(!(position == Position.AM || position == Position.RM || position == Position.CM)){
                org.assertj.core.api.Assertions.fail("fail");
            }

        }
    }
    @Test
    @DisplayName("선수 검색 동적 쿼리 -  이름 + 포지션")
    public void positionAndName() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setPlayerName("스");
        req.getPositions().add(Position.ST);

        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {
            org.assertj.core.api.Assertions.assertThat(playerSearchResponse.getPlayerName()).contains("스");
            Position position = playerSearchResponse.getPosition();
            System.out.println("position = " + position);
            if(position != Position.ST){
                org.assertj.core.api.Assertions.fail("fail");
            }

        }
    }


    @Test
    @DisplayName("전부 있을 떄")
    public void all() throws Exception{
        // given
        PlayerSearchRequest req = new PlayerSearchRequest();
        req.setPlayerName("스");
        req.getPositions().add(Position.ST);
        req.setLeagueId(1L);
        req.setTeamId(2L);
        // when
        List<PlayerSearchResponse> resp = playerSearch.playerSearch(req);
        // then
        for (PlayerSearchResponse playerSearchResponse : resp) {
            org.assertj.core.api.Assertions.assertThat(playerSearchResponse.getPlayerName()).contains("스");
            Position position = playerSearchResponse.getPosition();
            System.out.println("position = " + position);
            if(position != Position.ST){
                org.assertj.core.api.Assertions.fail("fail");
            }
            Player player = playerRepository.findById(playerSearchResponse.getPlayerId()).get();
            Team team = player.getTeam();
            org.assertj.core.api.Assertions.assertThat(team.getId()).isEqualTo(2L);
            org.assertj.core.api.Assertions.assertThat(team.getLeague().getId()).isEqualTo(1);
        }
    }








}