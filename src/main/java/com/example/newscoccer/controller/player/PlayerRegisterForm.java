package com.example.newscoccer.controller.player;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRegisterForm {
    private String playerName;
    private Long teamId;
    private Position position;
    private Stat stat;
}
