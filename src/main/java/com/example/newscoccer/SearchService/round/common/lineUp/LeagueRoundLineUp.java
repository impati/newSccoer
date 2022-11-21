package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.Round.*;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeagueRoundLineUp implements RoundLineUp {
    private final RoundUtils roundUtils;
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Override
    public boolean supports(Round round) {
        return round instanceof LeagueRound;
    }

    @Override
    public RoundLineUpResponse lineUp(RoundLineUpRequest req) {
        Round round = roundUtils.getRound(req.getRoundId());
        TeamLeagueRoundInner lineUp = new TeamLeagueRoundInner(teamLeagueRecordRepository.findByRound(round),round);
        lineUp.teamSetting();

        roundLineYet(lineUp);
        roundLineDone(lineUp);
        return lineUp.getResponse();
    }

    private void roundLineDone(TeamLeagueRoundInner lineUp) {
        if(lineUp.round.getRoundStatus() == RoundStatus.YET) return;
        lineUp.resp.setLineUpDone(true);
        lineUp.getResponse().setPlayerListA(playerLeagueRecordRepository.findByTeamAndRound(lineUp.getTeamA(),lineUp.getRound())
                .stream()
                .map(leagueRecord -> new RoundLineUpDto(leagueRecord.getPlayer().getId(),leagueRecord.getPlayer().getName(),leagueRecord.getPosition()))
                .collect(Collectors.toList()));
        lineUp.getResponse().setPlayerListB(playerLeagueRecordRepository.findByTeamAndRound(lineUp.getTeamB(),lineUp.getRound())
                .stream()
                .map(leagueRecord -> new RoundLineUpDto(leagueRecord.getPlayer().getId(),leagueRecord.getPlayer().getName(),leagueRecord.getPosition()))
                .collect(Collectors.toList()));
    }
    private void roundLineYet(TeamLeagueRoundInner lineUp){
        if(lineUp.round.getRoundStatus() != RoundStatus.YET) return;
        lineUp.getResponse().setPlayerListA(playerRepository.findByTeam(lineUp.getTeamA())
                .stream()
                .map(player->new RoundLineUpDto(player.getId(),player.getName(),player.getPosition()))
                .collect(Collectors.toList()));
        lineUp.getResponse().setPlayerListB(playerRepository.findByTeam(lineUp.getTeamB())
                .stream()
                .map(player->new RoundLineUpDto(player.getId(),player.getName(),player.getPosition()))
                .collect(Collectors.toList()));
    }

    static class TeamLeagueRoundInner {
        private final List<TeamLeagueRecord> teamLeagueRecords;
        private final Round round;
        private RoundLineUpResponse resp;
        TeamLeagueRoundInner(List<TeamLeagueRecord> teamLeagueRecords,Round round) {
            this.teamLeagueRecords = teamLeagueRecords;
            this.round = round ;
            resp = new RoundLineUpResponse();
        }
        private void teamSetting(){
            resp.teamAUpdate(getTeamA());
            resp.teamBUpdate(getTeamB());
        }
        private Round getRound(){return round;}
        private RoundLineUpResponse getResponse(){
            return resp;
        }
        private Team getTeamA(){
            return teamLeagueRecords.get(0).getTeam();
        }
        private Team getTeamB(){
            return teamLeagueRecords.get(1).getTeam();
        }
    }



    @Override
    public <T> T feature(RoundDto roundDto, Class<T> returnType) {
        return RoundLineUp.super.feature(roundDto, returnType);
    }

    @Override
    public void feature(RoundDto roundDto) {
        RoundLineUp.super.feature(roundDto);
    }
}
