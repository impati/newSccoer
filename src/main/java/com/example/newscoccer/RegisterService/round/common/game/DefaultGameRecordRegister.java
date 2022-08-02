package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Round.RoundTemplate;
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

import static java.util.stream.Collectors.toList;

/**
 * GameRecordDto 를 받아서 경기결과를 저장.
 * 역시나 반 복문제  분기는 싫다!
 */
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
                TeamLeagueRecord tlrA = tlrList.get(0); // teamId 가 더 작은 팀.
                TeamLeagueRecord tlrB = tlrList.get(1); // teamId 가 더 큰 팀.
                // 팀 데이터 저장.
                teamSolved(tlrA,dto);
                teamSolved(tlrB,dto);

                // 선수 데이터 저장, ps . playerLeagueRecordRepository 에서 round 로 조회해 올수도 있음.
                playerSolved(playerLeagueRecordRepository.findByTeamAndRound(tlrA.getTeam(),round).stream().collect(toList()), dto);
                playerSolved(playerLeagueRecordRepository.findByTeamAndRound(tlrB.getTeam(),round).stream().collect(toList()), dto);

                round.setRoundStatus(RoundStatus.RECORD);
                return null;
            }

            @Override
            public Void championsSolved() {
                List<TeamChampionsRecord > tcrList = teamChampionsRecordRepository.findByRound(round);
                TeamChampionsRecord tcrA = tcrList.get(0);
                TeamChampionsRecord tcrB = tcrList.get(0);

                // 팀 데이터 저장.
                teamSolved(tcrA,dto);
                teamSolved(tcrB,dto);

                // 선수 데이터 저장, ps . playerLeagueRecordRepository 에서 round 로 조회해 올수도 있음.
                playerSolved(playerChampionsRecordRepository.findByTeamAndRound(tcrA.getTeam(),round).stream().collect(toList()), dto);
                playerSolved(playerChampionsRecordRepository.findByTeamAndRound(tcrB.getTeam(),round).stream().collect(toList()), dto);

                round.setRoundStatus(RoundStatus.RECORD);



                return null;
            }
        };

        new RoundTemplate().action(round,roundFeature);

    }

    private void playerSolved(List<PlayerRecord> plrList , GameRecordDto dto){
        for (var playerRecord : plrList) {
            GameResultPlayerDto playerDto = dto.getPlayers()
                    .stream()
                    .filter(p->p.getPlayerId().equals(playerRecord.getPlayer().getId())).findFirst().orElse(null);
            playerRecord.update(playerDto.getGoal(),playerDto.getAssist(),playerDto.getPass(),
                    playerDto.getShooting(),playerDto.getValidShooting(),playerDto.getFoul(),playerDto.getDefense(),playerDto.getGrade());
        }
    }

    private void teamSolved(TeamRecord tlr  , GameRecordDto dto){
        GameResultTeamDto teamDto = dto.getTeams().stream().filter(t -> t.getTeamId().equals(tlr.getTeam().getId())).findFirst().orElse(null);
        tlr.update(teamDto.getScore(),teamDto.getShare(),teamDto.getCornerKick(), teamDto.getFreeKick());
    }


}
