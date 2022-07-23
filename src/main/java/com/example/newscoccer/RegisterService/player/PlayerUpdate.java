package com.example.newscoccer.RegisterService.player;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 선수 수정 및 등록 .
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlayerUpdate {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    /**
     * PlayerUpdateDto 를 이용하여 선수 등록
     * @param playerDto
     * @return
     */
    public Player registerPlayer(PlayerUpdateDto playerDto){
        Stat stat = Stat.createStat(
                playerDto.getAcceleration(), playerDto.getSpeed(), playerDto.getPhysicalFight(),
                playerDto.getStamina(), playerDto.getActiveness(), playerDto.getJump(),
                playerDto.getBalance(), playerDto.getBallControl(), playerDto.getCrosses(),
                playerDto.getPass(), playerDto.getLongPass(), playerDto.getDribble(),
                playerDto.getGoalDetermination(), playerDto.getMidRangeShot(), playerDto.getShootPower(),
                playerDto.getHeading(), playerDto.getDefense(), playerDto.getTackle(),
                playerDto.getIntercepting(), playerDto.getSlidingTackle(), playerDto.getDiving(),
                playerDto.getHandling(), playerDto.getGoalKick(), playerDto.getSpeedReaction(),
                playerDto.getPositioning(), playerDto.getVisualRange(), playerDto.getSense());

        Team team = teamRepository.findById(playerDto.getTeamId()).orElse(null);
        Player player = Player.createPlayer(playerDto.getName(),playerDto.getPosition(),team,stat);
        playerRepository.save(player);
        return player;
    }

    /**
     * PlayerUpdateDto 를 이용하여 선수 수정.
     * @param playerId
     * @param playerUpdateDto
     */
    public void editPlayer(Long playerId , PlayerUpdateDto playerUpdateDto){

        Player player = playerRepository.findById(playerId).orElse(null);
        Team team = teamRepository.findById(playerUpdateDto.getTeamId()).orElse(null);
        player.update(playerUpdateDto.getName(),
                    playerUpdateDto.getPosition(), team,
                playerUpdateDto.getAcceleration(),playerUpdateDto.getSpeed(),playerUpdateDto.getPhysicalFight(),
                playerUpdateDto.getStamina(),playerUpdateDto.getActiveness(),playerUpdateDto.getJump(),
                playerUpdateDto.getBalance(),playerUpdateDto.getBallControl(),playerUpdateDto.getCrosses(),
                playerUpdateDto.getPass(),playerUpdateDto.getLongPass(),playerUpdateDto.getDribble(),
                playerUpdateDto.getGoalDetermination(),playerUpdateDto.getMidRangeShot(),playerUpdateDto.getShootPower(),
                playerUpdateDto.getHeading(),playerUpdateDto.getDefense(),playerUpdateDto.getTackle(),
                playerUpdateDto.getIntercepting(),playerUpdateDto.getSlidingTackle(),playerUpdateDto.getDiving(),
                playerUpdateDto.getHandling(),playerUpdateDto.getGoalKick(),playerUpdateDto.getSpeedReaction(),
                playerUpdateDto.getPositioning(),playerUpdateDto.getVisualRange(),playerUpdateDto.getSense());

    }

}
