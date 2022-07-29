package com.example.newscoccer.SearchService.team.info.champions;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.team.ParticipatePlayer;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 시즌 팀의 챔피언스 정보를 가져온다.
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultTeamChampionsInfo implements EntityRecordInfo<TeamChampionsInfoRequest , TeamChampionsInfoResponse> {
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Override
    public TeamChampionsInfoResponse recordInfo(TeamChampionsInfoRequest req) {
        TeamChampionsInfoResponse resp = new TeamChampionsInfoResponse();
        List<TeamChampionsRecord> records = teamChampionsRecordRepository.findByTeamAndSeason(req.getTeamId(), req.getSeason());
        for (var record : records) {
            resp.setGain(resp.getGain() + record.getScore());
            resp.setLost(resp.getLost() + record.getOppositeScore());
            resp.setRank(record.getRank());
        }
        resp.setDiff(resp.getGain() - resp.getLost());

        MatchResultUtils matchResultUtils = new MatchResultUtils(records);
        resp.setWin(matchResultUtils.getWin());
        resp.setDraw(matchResultUtils.getDraw());
        resp.setLose(matchResultUtils.getLose());

        playerChampionsRecordRepository.findPlayerParticipate(req.getTeamId(), req.getSeason())
                .stream()
                .forEach(participate->{
                    Player p = participate.getPlayer();
                    resp.getParticipatePlayers().add(new ParticipatePlayer(p.getId(),p.getName(),p.getPosition(),p.getRating(), Math.toIntExact(participate.getParticipateCount())));
                });

        return resp;
    }
}
