package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 선수 아이디와 게임에 참가할 선수 포지션
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineUpResult {
    private Long playerId;
    private Position position;
}
