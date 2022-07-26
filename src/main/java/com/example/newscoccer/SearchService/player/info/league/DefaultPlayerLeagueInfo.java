package com.example.newscoccer.SearchService.player.info.league;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 시즌 + 선수 정보로  리그 정보를 가져옴.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultPlayerLeagueInfo implements EntityRecordInfo<PlayerLeagueInfoRequest,PlayerLeagueInfoResponse> {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Override
    public PlayerLeagueInfoResponse recordInfo(PlayerLeagueInfoRequest req) {
        List<PlayerLeagueRecord> playerLeagueRecordList  = playerLeagueRecordRepository.findByPlayerAndSeason(req.getPlayerId(),req.getSeason());
        PlayerLeagueInfoResponse resp = new PlayerLeagueInfoResponse();
        double avgGrade = 0;
        int sz = playerLeagueRecordList.size();
        if(sz == 0) return resp;
        else {
            for (var record : playerLeagueRecordList) {

                resp.setGoal(resp.getGoal() + record.getGoal());
                resp.setAssist(resp.getAssist() + record.getAssist());
                resp.setPass(resp.getPass() + record.getPass());
                resp.setShooting(resp.getShooting() + record.getShooting());
                resp.setValidShooting(resp.getValidShooting() + record.getValidShooting());
                resp.setFoul(resp.getFoul() + record.getFoul());
                resp.setDefense(resp.getDefense() + record.getGoodDefense());
                int isBest = record.isBest() ? 1 : 0;
                resp.setIsBest(resp.getIsBest() + isBest);
                resp.setRating(record.getRating());
                resp.setRank(record.getRank());
                avgGrade += record.getGrade();
            }
            resp.setGameNumber(sz);
            avgGrade /= sz;
            resp.setAvgGrade(Math.round(avgGrade * 100) / 100.0);
            MatchResultUtils matchResultUtils = new MatchResultUtils(playerLeagueRecordList);
            resp.setWin(matchResultUtils.getWin());
            resp.setDraw(matchResultUtils.getDraw());
            resp.setLose(matchResultUtils.getLose());

            return resp;
        }
    }
}
