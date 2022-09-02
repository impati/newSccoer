package com.example.newscoccer.SearchService.round.common.goalAssistPair;

import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.record.Duo;
import com.example.newscoccer.springDataJpa.DuoRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultGoalAssistPairSearch implements GoalAssistPairSearch{
    private final DuoRepository duoRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    @Override
    public List<GoalAssistPairInfo> goalAssistPairSearch(Long roundId) {

        List<Duo> duoList = duoRepository.findByRound(roundId);
        Set<Long> playerList = new HashSet<>();
        duoList.stream().forEach(p->{
            playerList.add(p.getAssistPlayerId());
            playerList.add(p.getGoalPlayerId());
        });
        List<Player> players = playerRepository.findByPlayerList(playerList.stream().collect(toList()));
        Map<Long , String> playerNameList = new HashMap<>();
        for(var p : players) playerNameList.put(p.getId(),p.getName());


        return duoList.stream()
                .map(ele-> new GoalAssistPairInfo(playerNameList.get(ele.getGoalPlayerId()),playerNameList.get(ele.getAssistPlayerId()),ele.getGoalType()))
                .collect(toList());
    }
}
