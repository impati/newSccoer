package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
class PlayerRecordTest {


    @Test
    void playerRecordReturnTest(){
        Round leagueRound = new LeagueRound();
        Round championsRound = new ChampionsRound();
        PlayerRecord playerRecord = PlayerRecord.createPlayerRecord(null, Position.AM,null,championsRound);
        Assertions.assertThat(playerRecord).isInstanceOf(PlayerChampionsRecord.class);
    }
}