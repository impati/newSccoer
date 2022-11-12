package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.*;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChampionsLineUpRegister implements LineUpRegister{
    private final RoundUtils roundUtils;
    private final PlayerRepository playerRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Override
    public boolean supports(Round round) {
        return round instanceof ChampionsRound;
    }

    @Override
    public void feature(RoundDto roundDto) {
        lineUpRegister((LineUpResultDto) roundDto);
    }

    @Override
    public void lineUpRegister(LineUpResultDto lineUpResultDto) {
        Round round  = roundUtils.getRound(lineUpResultDto.getRoundId());
        lineUpResultDto.getParticipatePlayer()
                .stream()
                .forEach(p->{
                    Player player = playerRepository.findById(p.getPlayerId()).orElse(null);
                    PlayerChampionsRecord pcr = (PlayerChampionsRecord)
                            PlayerChampionsRecord.createPlayerRecord(player,p.getPosition(),player.getTeam(),round);
                    playerChampionsRecordRepository.save(pcr);
                });
        round.setRoundStatus(RoundStatus.ING);
    }

}
