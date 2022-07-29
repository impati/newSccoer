package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.dto.PlayerLeagueParticipate;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PlayerLeagueRecordRepositoryTest {

    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    RoundRepository roundRepository;



    @Test
    @DisplayName("선수와 리그 경기 참가 수 - 한명 이상")
    public void findByRoundAndTeam() throws Exception{
        // given

        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();

        Player player = maker.getPlayer();

        Team team  = maker.getTeam();
        Player player1 = Player.createPlayer("testNumber2",Position.CM,team,new Stat());
        playerRepository.save(player1);



        for(int i = 1;i<= SeasonUtils.lastLeagueRoundSt;i++){
            LeagueRound leagueRound = new LeagueRound(0,i);
            roundRepository.save(leagueRound);
            PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(player, player.getPosition(),team,leagueRound);
            playerLeagueRecordRepository.save(plr);

        }
        for(int i = 1;i<= SeasonUtils.lastLeagueRoundSt;i++){
            LeagueRound leagueRound = new LeagueRound(0,i);
            roundRepository.save(leagueRound);
            PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(player1, player1.getPosition(),team,leagueRound);
            playerLeagueRecordRepository.save(plr);

        }

        // when
        List<PlayerLeagueParticipate> playerParticipate = playerLeagueRecordRepository.findPlayerParticipate(team.getId(), 0);
        // then
        Assertions.assertThat(playerParticipate.size()).isEqualTo(2);


        PlayerLeagueParticipate p = playerParticipate.get(0);
        Assertions.assertThat(p.getParticipateCount()).isEqualTo(45);
        Assertions.assertThat(p.getPlayer().getId()).isEqualTo(player.getId());

        PlayerLeagueParticipate p1 = playerParticipate.get(1);
        Assertions.assertThat(p1.getParticipateCount()).isEqualTo(45);
        Assertions.assertThat(p1.getPlayer().getId()).isEqualTo(player1.getId());



    }
    @Test
    @DisplayName("선수와 리그 경기 참가 수 - 한명")
    public void findByRoundAndTeam1() throws Exception{
        // given

        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();

        Player player = maker.getPlayer();
        Team team = maker.getTeam();

        for(int i = 1;i<= SeasonUtils.lastLeagueRoundSt;i++){
            LeagueRound leagueRound = new LeagueRound(0,i);
            roundRepository.save(leagueRound);
            PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(player, player.getPosition(),team,leagueRound);
            playerLeagueRecordRepository.save(plr);

        }


        // when
        List<PlayerLeagueParticipate> playerParticipate = playerLeagueRecordRepository.findPlayerParticipate(team.getId(), 0);
        // then
        Assertions.assertThat(playerParticipate.size()).isEqualTo(1);


        PlayerLeagueParticipate p = playerParticipate.get(0);
        Assertions.assertThat(p.getParticipateCount()).isEqualTo(45);
        Assertions.assertThat(p.getPlayer().getId()).isEqualTo(player.getId());




    }
}