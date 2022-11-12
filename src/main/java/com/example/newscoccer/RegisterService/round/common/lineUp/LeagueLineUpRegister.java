package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.*;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LeagueLineUpRegister implements LineUpRegister{
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final RoundUtils roundUtils;
    @Override
    public boolean supports(Round round) {
        return round instanceof LeagueRound;
    }

    @Override
    public void lineUpRegister(LineUpResultDto lineUpResultDto) {
        Round round = roundUtils.getRound(lineUpResultDto.getRoundId());
        lineUpResultDto.getParticipatePlayer()
                .stream()
                .forEach(p->{
                    Player player = playerRepository.findById(p.getPlayerId()).orElse(null);
                    PlayerLeagueRecord plr = (PlayerLeagueRecord)
                            PlayerLeagueRecord.createPlayerRecord(player,p.getPosition(),player.getTeam(),round);
                    playerLeagueRecordRepository.save(plr);
                });
        round.setRoundStatus(RoundStatus.ING);
    }

    @Override
    public void feature(RoundDto roundDto) {
        lineUpRegister((LineUpResultDto) roundDto);
    }
}
