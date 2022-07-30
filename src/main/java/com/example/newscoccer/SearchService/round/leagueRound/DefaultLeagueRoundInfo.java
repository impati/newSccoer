package com.example.newscoccer.SearchService.round.leagueRound;

import com.example.newscoccer.SearchService.round.RoundInfoDto;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  리그 , 시즌 , 라운드st 정보를 통해 매치업을 가져옴.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultLeagueRoundInfo implements LeagueRoundInfo{
    private final RoundRepository roundRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;

    @Override
    public LeagueRoundInfoResponse leagueRoundInformation(LeagueRoundInfoRequest req) {
        LeagueRoundInfoResponse resp = new LeagueRoundInfoResponse();

        Map<Long, RoundInfoDto> mapped = new HashMap<>();

        List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository
                .findLeagueRoundInfo(roundRepository.findByLeagueRoundInfo(req.getLeagueId(),req.getSeason(),req.getRoundSt()));


        teamLeagueRecordList.stream().forEach(tlr->{
            if(mapped.containsKey(tlr.getRound().getId())){
                Round round = tlr.getRound();
                RoundInfoDto roundInfoDto = mapped.get(round.getId());
                roundInfoDto.setTeamB(tlr.getTeam());
                roundInfoDto.setScoreB(tlr.getScore());
            }
            else{
                Round round = tlr.getRound();
                RoundInfoDto roundInfoDto = new RoundInfoDto();
                roundInfoDto.setTeamA(tlr.getTeam());
                roundInfoDto.setScoreA(tlr.getScore());
                roundInfoDto.setDone(round.getRoundStatus() == RoundStatus.DONE ? true : false);
                mapped.put(round.getId(),roundInfoDto);
            }

        });

        for(var set : mapped.keySet()){
            resp.getRoundInfoDtoList().add(mapped.get(set));
        }

        return resp;
    }
}
