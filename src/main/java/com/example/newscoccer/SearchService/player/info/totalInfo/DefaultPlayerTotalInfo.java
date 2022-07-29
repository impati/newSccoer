package com.example.newscoccer.SearchService.player.info.totalInfo;

import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.PlayerRecord;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DefaultPlayerTotalInfo implements EntityTotalInfo<PlayerTotalInfoRequest , PlayerTotalInfoResponse> {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    //TODO : OTHER
    @Override
    public PlayerTotalInfoResponse totalInfo(PlayerTotalInfoRequest req) {
        PlayerTotalInfoResponse resp = new PlayerTotalInfoResponse();

        List<PlayerLeagueRecord> playerLeagueAll = playerLeagueRecordRepository.findPlayerLeagueAll(req.getPlayerId());
        standardCalc(resp,playerLeagueAll.stream().collect(Collectors.toList()), PlayerLeagueRecord.class);

        List<PlayerChampionsRecord> playerChampionsAll = playerChampionsRecordRepository.findPlayerChampionsAll(req.getPlayerId());
        standardCalc(resp,playerChampionsAll.stream().collect(Collectors.toList()), PlayerChampionsRecord.class);

        if(resp.getGameNumber() != 0)
            resp.setAvgGrade(Math.round((resp.getAvgGrade()/resp.getGameNumber())*100) / 100.0);

        return resp;
    }

    private void standardCalc(PlayerTotalInfoResponse resp , List<PlayerRecord> playerRecords, Class clazz){
        int sz = playerRecords.size();
        resp.calcRank(playerRecords, clazz);
        playerRecords.stream().forEach(pr -> {
            resp.setGoal(resp.getGoal() + pr.getGoal());
            resp.setAssist(resp.getAssist() + pr.getAssist());
            resp.setPass(resp.getPass() + pr.getPass());
            resp.setShooting(resp.getShooting() + pr.getShooting());
            resp.setValidShooting(resp.getValidShooting() + pr.getValidShooting());
            resp.setDefense(resp.getDefense() + pr.getGoodDefense());
            resp.setFoul(resp.getFoul() + pr.getFoul());
            resp.setAvgGrade(resp.getAvgGrade() + pr.getGrade());
            resp.setIsBest(resp.getIsBest() + (pr.isBest() ? 1 : 0));
        });
        resp.setGameNumber(resp.getGameNumber() + sz);
        MatchResultUtils matchResultUtils = new MatchResultUtils(playerRecords);
        resp.setWin(resp.getWin() + matchResultUtils.getWin());
        resp.setDraw(resp.getDraw() + matchResultUtils.getDraw());
        resp.setLose(resp.getLose() + matchResultUtils.getLose());
    }

}
