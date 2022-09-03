package com.example.newscoccer.SearchService.round.common.game;

import com.example.newscoccer.domain.Round.*;
import com.example.newscoccer.domain.record.PlayerRecord;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.domain.record.TeamRecord;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 리그 , 챔피언스리그 , ..
 * 경기 결과 OR 경기 전 정보를 가져옴 .
 * TODO : 반복 문제 해결 .
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultGameResult implements GameResult{
    private final RoundRepository roundRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Override
    public GameResultResponse gameResult(GameResultRequest req) {
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        GameResultResponse resp = gameRecord(round);
        if(round.getRoundStatus() == RoundStatus.DONE || round.getRoundStatus() == RoundStatus.PAIR) resp.setIsDone(true);
        else resp.setIsDone(false);
        return resp;
    }




    private GameResultResponse gameRecord(Round round){

        RoundFeature<GameResultResponse> roundFeature = new RoundFeature<>() {
            @Override
            public GameResultResponse leagueSolved() {
                GameResultResponse resp = new GameResultResponse();
                List<TeamLeagueRecord> tlr = teamLeagueRecordRepository.findByRound(round);

                resp.setTeamA(teamSetting(tlr.get(0)));
                playerLeagueRecordRepository.findByTeamAndRound(tlr.get(0).getTeam(),round)
                        .stream()
                        .forEach(plr->{resp.getPlayerADtoList().add(playerSetting(plr));});


                resp.setTeamB(teamSetting(tlr.get(1)));
                playerLeagueRecordRepository.findByTeamAndRound(tlr.get(1).getTeam(),round)
                        .stream()
                        .forEach(plr->{resp.getPlayerBDtoList().add(playerSetting(plr));});

                return resp;
            }
            @Override
            public GameResultResponse championsSolved() {
                GameResultResponse resp = new GameResultResponse();
                List<TeamChampionsRecord> tcr = teamChampionsRecordRepository.findByRound(round);

                resp.setTeamA(teamSetting(tcr.get(0)));
                playerChampionsRecordRepository.findByTeamAndRound(tcr.get(0).getTeam(),round)
                        .stream()
                        .forEach(plr->{resp.getPlayerADtoList().add(playerSetting(plr));});


                resp.setTeamB(teamSetting(tcr.get(1)));
                playerChampionsRecordRepository.findByTeamAndRound(tcr.get(1).getTeam(),round)
                        .stream()
                        .forEach(plr->{resp.getPlayerBDtoList().add(playerSetting(plr));});


                return resp;
            }
        };

        return new RoundTemplate().action(round,roundFeature);

    }




    private GameResultTeamDto teamSetting(TeamRecord tlr){
        return new GameResultTeamDto(tlr.getTeam().getId(),tlr.getTeam().getName(),
                tlr.getScore(),tlr.getShare(),tlr.getCornerKick(),tlr.getFreeKick());

    }

    private GameResultPlayerDto playerSetting(PlayerRecord pr){
        return new GameResultPlayerDto(pr.getPlayer().getId(),pr.getPlayer().getName(),pr.getPosition(),pr.getGoal(),
                    pr.getAssist(),pr.getPass(),pr.getShooting(),pr.getValidShooting(),pr.getFoul(),pr.getGoodDefense(),pr.getGrade()
                );
    }


}
