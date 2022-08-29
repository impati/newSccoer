package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * RoundStatus.INIT
 *  ->팀의 선수들을 모두 가져옴 .
 *  ->팀 A , 팀 B
 *
 * RoundStatus.YET
 *  ->저장된 라인업을 가져와서 리턴.
 *
 *
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultRoundLineUp implements RoundLineUp{
    private final RoundRepository roundRepository;
    private final PlayerRepository playerRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Override
    public RoundLineUpResponse lineUp(RoundLineUpRequest req) {
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);

        RoundTemplate roundTemplate = new RoundTemplate();

        RoundFeature<RoundLineUpResponse> roundFeature = new RoundFeature<>() {
            @Override
            public RoundLineUpResponse leagueSolved() {
                RoundLineUpResponse resp = new RoundLineUpResponse();
                resp.setLineUpDone(false);
                List<TeamLeagueRecord> tlr = teamLeagueRecordRepository.findByRound(round);
                Team teamA = tlr.get(0).getTeam();
                Team teamB = tlr.get(1).getTeam();

                resp.teamAUpdate(teamA);
                resp.teamBUpdate(teamB);

                //라인업이 결정되지 않았을 때
                if(round.getRoundStatus() == RoundStatus.INIT) {
                    playerRepository.findByTeam(teamA).stream().forEach(p -> {
                        resp.getPlayerListA().add(new RoundLineUpDto(p.getId(), p.getName(), p.getPosition()));
                    });
                    playerRepository.findByTeam(teamB).stream().forEach(p -> {
                        resp.getPlayerListB().add(new RoundLineUpDto(p.getId(), p.getName(), p.getPosition()));
                    });
                }
                else{// 라인업이 결정되었을 때
                    resp.setLineUpDone(true);
                    resp.setPlayerListA(playerLeagueRecordRepository.findByTeamAndRound(teamA,round).stream()
                            .map(plr->new RoundLineUpDto(plr.getPlayer().getId(), plr.getPlayer().getName(), plr.getPosition()))
                            .collect(toList()));

                    resp.setPlayerListB(playerLeagueRecordRepository.findByTeamAndRound(teamB,round).stream()
                            .map(plr->new RoundLineUpDto(plr.getPlayer().getId(), plr.getPlayer().getName(), plr.getPosition()))
                            .collect(toList()));
                }
                positionSort(resp);

                return resp;
            }

            @Override
            public RoundLineUpResponse championsSolved() {
                RoundLineUpResponse resp = new RoundLineUpResponse();
                List<TeamChampionsRecord> tcr = teamChampionsRecordRepository.findByRound(round);

                Team teamA = tcr.get(0).getTeam();
                Team teamB = tcr.get(1).getTeam();

                resp.teamAUpdate(teamA);
                resp.teamBUpdate(teamB);

                //라인업이 결정되지 않았을 때
                if(round.getRoundStatus() == RoundStatus.INIT) {
                    playerRepository.findByTeam(teamA).stream().forEach(p -> {
                        resp.getPlayerListA().add(new RoundLineUpDto(p.getId(), p.getName(), p.getPosition()));
                    });
                    playerRepository.findByTeam(teamB).stream().forEach(p -> {
                        resp.getPlayerListB().add(new RoundLineUpDto(p.getId(), p.getName(), p.getPosition()));
                    });
                }
                else{ // 라인업이 결정되었을 때

                    resp.setPlayerListA(playerChampionsRecordRepository.findByTeamAndRound(teamA,round).stream()
                            .map(plr->new RoundLineUpDto(plr.getPlayer().getId(), plr.getPlayer().getName(), plr.getPosition()))
                            .collect(toList()));
                    resp.setPlayerListB(playerChampionsRecordRepository.findByTeamAndRound(teamB,round).stream()
                            .map(plr->new RoundLineUpDto(plr.getPlayer().getId(), plr.getPlayer().getName(), plr.getPosition()))
                            .collect(toList()));

                }
                positionSort(resp);
                return resp;
            }
        };


        return roundTemplate.action(round,roundFeature);
    }

    private void positionSort(RoundLineUpResponse resp){
        resp.getPlayerListA().sort((e1,e2)->{
            if(e1.getPosition().ordinal() > e2.getPosition().ordinal()) return 1;
            else if(e1.getPosition().ordinal() > e2.getPosition().ordinal()) return 0;
            else return -1;
        });

        resp.getPlayerListB().sort((e1,e2)->{
            if(e1.getPosition().ordinal() > e2.getPosition().ordinal()) return 1;
            else if(e1.getPosition().ordinal() > e2.getPosition().ordinal()) return 0;
            else return -1;
        });
    }

}
