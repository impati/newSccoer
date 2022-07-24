package com.example.newscoccer.SearchService.player.info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 선수의 간단한 정보 리턴
 * 이름 , 팀 이름 , 포지션 , 레이팅
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultPlayerSimpleInfo implements EntitySimpleInfo<PlayerSimpleInfoRequest , PlayerSimpleInfoResponse> {
    private final PlayerRepository playerRepository;
    @Override
    public PlayerSimpleInfoResponse simpleInfo(PlayerSimpleInfoRequest req) {
        Player player = playerRepository.findById(req.getPlayerId()).orElse(null);
        return new PlayerSimpleInfoResponse(player.getName(),player.getTeam().getName(),player.getPosition(), player.getRating());
    }
}
