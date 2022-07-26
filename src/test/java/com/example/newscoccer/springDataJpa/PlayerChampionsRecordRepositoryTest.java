package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.testSupport.LeagueTeamPlayer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class PlayerChampionsRecordRepositoryTest {

    @Autowired
    PlayerChampionsRecordRepository playerChampionsRecordRepository;

    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    RoundRepository roundRepository;
    @Test
    @DisplayName("선수 + 시즌 정보로 챔피언스 기록 가져오기 ")
    public void findPlayerAndSeason() throws Exception{
        // given
        LeagueTeamPlayer maker = new LeagueTeamPlayer(leagueRepository,teamRepository,playerRepository);
        maker.init();
        Player player = maker.getPlayer();
        int season = 600;
        int roundSt = 10 ;
        for(int i = 0 ;i<roundSt;i++){
            ChampionsRound round = new ChampionsRound(season,i,1);
            roundRepository.save(round);
            PlayerChampionsRecord playerChampionsRecord = (PlayerChampionsRecord) PlayerChampionsRecord.createPlayerRecord(player, Position.ST,player.getTeam(),round);
            playerChampionsRecordRepository.save(playerChampionsRecord);
        }
        // when
        List<PlayerChampionsRecord> resp = playerChampionsRecordRepository.findByPlayerAndSeason(player.getId(), season);
        // then
        Assertions.assertThat(resp.size()).isEqualTo(roundSt);


    }
}