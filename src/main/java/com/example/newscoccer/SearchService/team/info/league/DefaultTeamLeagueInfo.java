package com.example.newscoccer.SearchService.team.info.league;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.team.ParticipatePlayer;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 시즌 팀의 리그 정보를 가져온다.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultTeamLeagueInfo implements EntityRecordInfo<TeamLeagueInfoRequest ,TeamLeagueInfoResponse> {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Override
    public TeamLeagueInfoResponse recordInfo(TeamLeagueInfoRequest req) {
        TeamLeagueInfoResponse resp = new TeamLeagueInfoResponse();
        List<TeamLeagueRecord>  teamLeagueRecordList = teamLeagueRecordRepository.findByTeamAndSeason(req.getTeamId(), req.getSeason());
        resp.setGameNumber(teamLeagueRecordList.size());
        for (var record : teamLeagueRecordList) {
            resp.setRank(record.getRank());
            resp.setGain(resp.getGain() + record.getScore());
            resp.setLost(resp.getLost() + record.getOppositeScore());

        }
        resp.setDiff(resp.getGain() - resp.getLost());
        MatchResultUtils matchResultUtils = new MatchResultUtils(teamLeagueRecordList);
        resp.setWin(matchResultUtils.getWin());
        resp.setDraw(matchResultUtils.getDraw());
        resp.setLose(matchResultUtils.getLose());
        resp.setPoint(matchResultUtils.getWin()*3 + matchResultUtils.getDraw());


        playerLeagueRecordRepository.findPlayerParticipate(req.getTeamId(),req.getSeason())
                .stream()
                .forEach(participatePlayer ->{
                    Player player =  participatePlayer.getPlayer();
                    resp.getParticipatePlayers()
                            .add(new ParticipatePlayer(player.getId(),player.getName(),player.getPosition(),player.getRating(), Math.toIntExact(participatePlayer.getParticipateCount())));
                });

        resp.sortParticipatePlayerByRating();
        return resp;
    }
}
