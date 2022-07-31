package com.example.newscoccer.SearchService.round.common.lineUp;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultRoundLineUp implements RoundLineUp{
    private final RoundRepository roundRepository;
    private final PlayerRepository playerRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Override
    public RoundLineUpResponse lineUp(RoundLineUpRequest req) {
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);

        RoundTemplate roundTemplate = new RoundTemplate();

        RoundFeature<RoundLineUpResponse> roundFeature = new RoundFeature<RoundLineUpResponse>() {
            @Override
            public RoundLineUpResponse leagueSolved() {
                RoundLineUpResponse resp = new RoundLineUpResponse();
                List<TeamLeagueRecord> tlr = teamLeagueRecordRepository.findByRound(round);

                Team teamA = tlr.get(0).getTeam();
                resp.setTeamAName(teamA.getName());
                playerRepository.findByTeam(teamA).stream().forEach(p->{
                    resp.getPlayerListA().add(new RoundLineUpDto(p.getId(),p.getName(),p.getPosition()));
                });

                Team teamB = tlr.get(1).getTeam();
                resp.setTeamBName(teamB.getName());
                playerRepository.findByTeam(teamB).stream().forEach(p->{
                    resp.getPlayerListB().add(new RoundLineUpDto(p.getId(),p.getName(),p.getPosition()));
                });

                return resp;
            }

            @Override
            public RoundLineUpResponse championsSolved() {
                RoundLineUpResponse resp = new RoundLineUpResponse();
                List<TeamChampionsRecord> tcr = teamChampionsRecordRepository.findByRound(round);

                Team teamA = tcr.get(0).getTeam();
                resp.setTeamAName(teamA.getName());
                playerRepository.findByTeam(teamA).stream().forEach(p->{
                    resp.getPlayerListA().add(new RoundLineUpDto(p.getId(),p.getName(),p.getPosition()));
                });

                Team teamB = tcr.get(1).getTeam();
                resp.setTeamBName(teamB.getName());
                playerRepository.findByTeam(teamB).stream().forEach(p->{
                    resp.getPlayerListB().add(new RoundLineUpDto(p.getId(),p.getName(),p.getPosition()));
                });

                return resp;
            }
        };


        return roundTemplate.action(round,roundFeature);
    }

}
