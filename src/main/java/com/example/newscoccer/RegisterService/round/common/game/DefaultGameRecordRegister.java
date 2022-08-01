package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultGameRecordRegister implements GameRecordRegister{
    private final RoundRepository roundRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Override
    public void gameRecordRegister(GameRecordDto dto) {

        Round round = roundRepository.findById(dto.getRoundId()).orElse(null);

        RoundFeature<Void> roundFeature = new RoundFeature<>() {
            @Override
            public Void leagueSolved() {
                List<TeamLeagueRecord> tlrList = teamLeagueRecordRepository.findByRound(round);
                TeamLeagueRecord tlrA = tlrList.get(0);
                TeamLeagueRecord tlrB = tlrList.get(1);

                teamSolved(tlrA,dto);
                teamSolved(tlrB,dto);


                playerSolved(playerLeagueRecordRepository.findByTeamAndRound(tlrA.getTeam(),round),dto);
                playerSolved(playerLeagueRecordRepository.findByTeamAndRound(tlrB.getTeam(),round),dto);


                return null;
            }

            @Override
            public Void championsSolved() {
                return null;
            }
        };

        new RoundTemplate().action(round,roundFeature);

    }

    private void playerSolved(List<PlayerLeagueRecord> plrList , GameRecordDto dto){
        for (var playerLeagueRecord : plrList) {
            GameResultPlayerDto playerDto = dto.getPlayers()
                    .stream()
                    .filter(p->p.getPlayerId().equals(playerLeagueRecord.getPlayer().getId())).findFirst().orElse(null);
            playerLeagueRecord.update(playerDto.getGoal(),playerDto.getAssist(),playerDto.getPass(),
                    playerDto.getShooting(),playerDto.getValidShooting(),playerDto.getFoul(),playerDto.getDefense(),playerDto.getGrade());
        }
    }

    private void teamSolved(TeamLeagueRecord tlr  ,GameRecordDto dto){
        GameResultTeamDto teamDto = dto.getTeams().stream().filter(t -> t.getTeamId().equals(tlr.getTeam().getId())).findFirst().orElse(null);
        tlr.update(teamDto.getScore(),teamDto.getShare(),teamDto.getCornerKick(), teamDto.getFreeKick());
    }


}
