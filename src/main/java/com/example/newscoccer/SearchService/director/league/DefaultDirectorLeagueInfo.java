package com.example.newscoccer.SearchService.director.league;

import com.example.newscoccer.SearchService.common.EntityLeagueInfo;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultDirectorLeagueInfo implements EntityLeagueInfo<DirectorLeagueInfoRequest,DirectorLeagueInfoResponse> {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Override
    public DirectorLeagueInfoResponse leagueInfo(DirectorLeagueInfoRequest req) {
        int rank = 0 ;
        List<TeamLeagueRecord> result = teamLeagueRecordRepository.findByDirectorAndSeason(req.getDirectorId(), req.getSeason());
        MatchResultUtils mat = new MatchResultUtils(result);
        for (var teamLeagueRecord : result) {
            rank = teamLeagueRecord.getRank();
        }
        return new DirectorLeagueInfoResponse(rank,mat.getWin(),mat.getDraw(),mat.getLose());
    }

}

