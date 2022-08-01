package com.example.newscoccer.RegisterService.round.common.lineUp;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * All type line up 저장을 처리
 * playerRecord  를 저장험,
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultLineUpRegister implements LineUpRegister{
    private final RoundRepository roundRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;

    private final PlayerRepository playerRepository;
    @Override
    public void lineUpRegister(LineUpResultDto dto) {
        Round round = roundRepository.findById(dto.getRoundId()).orElse(null);


        RoundFeature<Void> roundFeature = new RoundFeature<>() {
            @Override
            public Void leagueSolved() {
                dto.getParticipatePlayer()
                        .stream()
                        .forEach(p->{
                            Player player = playerRepository.findById(p.getPlayerId()).orElse(null);
                            PlayerLeagueRecord plr = (PlayerLeagueRecord)
                                    PlayerLeagueRecord.createPlayerRecord(player,p.getPosition(),player.getTeam(),round);
                            playerLeagueRecordRepository.save(plr);
                        });

                return null;
            }

            @Override
            public Void championsSolved() {

                dto.getParticipatePlayer()
                        .stream()
                        .forEach(p->{
                            Player player = playerRepository.findById(p.getPlayerId()).orElse(null);
                            PlayerChampionsRecord pcr = (PlayerChampionsRecord)
                                    PlayerChampionsRecord.createPlayerRecord(player,p.getPosition(),player.getTeam(),round);
                            playerChampionsRecordRepository.save(pcr);
                        });


                return null;
            }
        };


        new RoundTemplate().action(round,roundFeature);
    }
}
