package com.example.newscoccer.auto.lineUp;

import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpRegister;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResult;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResultDto;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUp;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpRequest;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpResponse;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *  자동 라인 업 저장 기능.
 *
 *  1. RoundLineUp -> 라인업을 등록할 수 있는 선수들을 가져옴 .
 *
 *
 *  2. 라인업 등록할 선수들을 결정
 *    -> 2 - 1 어떤 기준으로 선수들을 선택할 것이냐 . -> Main = true 인 선수들을 선택.
 *
 *  3. 2번의 선수들을 LineUpRegister 이용하여 등록.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class DefaultAutoLineUp implements AutoLineUp{
    private final RoundLineUp roundLineUp;
    private final LineUpRegister lineUpRegister;
    private final PlayerRepository playerRepository;

    @Override
    public void autoLineUp(Long roundId) {
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));
        LineUpResultDto result = new LineUpResultDto();
        result.setRoundId(roundId);

        // LineUpResult 생성해서 넣어줌 .

        //팀A
        List<Long> playerIdAList = resp.getPlayerListA().stream().map(ele->ele.getPlayerId()).collect(toList());
        playerRepository.findByPlayerList(playerIdAList).stream().filter(p->p.isMain()).forEach(ele->{
            result.getParticipatePlayer().add(new LineUpResult(ele.getId(),ele.getPosition()));
        });

        //팀B
        List<Long> playerIdBList = resp.getPlayerListB().stream().map(ele->ele.getPlayerId()).collect(toList());

        playerRepository.findByPlayerList(playerIdBList).stream().filter(p->p.isMain()).forEach(ele->{
            result.getParticipatePlayer().add(new LineUpResult(ele.getId(),ele.getPosition()));
        });
        lineUpRegister.lineUpRegister(result);
    }
}
