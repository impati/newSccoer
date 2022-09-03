package com.example.newscoccer.RegisterService.round.common.GoalAssistPair;

import com.example.newscoccer.RegisterService.round.GameDoneTroubleShooter;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.record.Duo;
import com.example.newscoccer.springDataJpa.DuoRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * lineUp -> game -> goalAssistPair //경기 끝. -> gameDoneTroubleShooter 호출해줌 .
 *  리그 , 챔피언스리그 , 유로파 공통 기능
 *
 *  하지만
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultGoalAssistPair implements GoalAssistPair{
    private final DuoRepository duoRepository;
    private final GameDoneTroubleShooter gameDoneTroubleShooter;
    private final RoundRepository roundRepository;
    @Override
    public void goalPairRegister(List<GoalAssistPairDto> pairList, Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);
        pairList.stream().forEach(d->{
            Duo duo = Duo.create(d.getGoalPlayer(),d.getAssistPlayer(),d.getGoalType(),round);
            duoRepository.save(duo);
        });
        round.setRoundStatus(RoundStatus.DONE);
        gameDoneTroubleShooter.AfterGameDone(round);
    }
}
