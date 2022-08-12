package com.example.newscoccer.auto;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AutoStatConfigTest {
    @Test
    @DisplayName("스탯 자동 세팅 기능")
    public void playerStat() throws Exception{
        // given
        List<Player> playerList = new ArrayList<>();
        Position []positions = Position.values();
        for (var position : positions) {
            int n = 5;
            while(n != 0) {
                playerList.add(Player.createPlayer("player", position, null, new Stat()));
                n -= 1;
            }
        }
//        // when
//        AutoStatConfig statConfig = new AutoStatConfig();
//        statConfig.playerStatConfig(playerList);
//
//        // then
//
//        playerList.stream()
//                .forEach(p->{
//                    System.out.println("position" + p.getPosition() + " p stat = " + p.getStat());
//                });


    }
}