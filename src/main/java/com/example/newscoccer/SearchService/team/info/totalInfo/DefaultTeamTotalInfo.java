package com.example.newscoccer.SearchService.team.info.totalInfo;

import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamRecord;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
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
public class DefaultTeamTotalInfo implements EntityTotalInfo<TeamTotalInfoRequest, TeamTotalInfoResponse> {

    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    //TODO : OTHER
    @Override
    public TeamTotalInfoResponse totalInfo(TeamTotalInfoRequest req) {
        TeamTotalInfoResponse resp = new TeamTotalInfoResponse();
        //리그
        standardCalc(
                teamLeagueRecordRepository.findByTeam(req.getTeamId()).stream().collect(Collectors.toList()),
                resp);

        // 챔피언스 리그
        standardCalc(
                teamChampionsRecordRepository.findByTeam(req.getTeamId()).stream().collect(Collectors.toList()),
                resp);




        return resp;
    }

    private void standardCalc(List<TeamRecord> teamChampionsRecords, TeamTotalInfoResponse resp) {
        for (var record : teamChampionsRecords) {
            resp.setGain(resp.getGain() + record.getScore());
            resp.setLost(resp.getLost() + record.getOppositeScore());
        }
        resp.setDiff(resp.getGain() - resp.getLost());
        MatchResultUtils matchResultUtils = new MatchResultUtils(teamChampionsRecords);
        resp.setWin(matchResultUtils.getWin());
        resp.setDraw(matchResultUtils.getDraw());
        resp.setLose(matchResultUtils.getLose());
        resp.calcRank(teamChampionsRecords,TeamChampionsRecord.class);
    }
}
