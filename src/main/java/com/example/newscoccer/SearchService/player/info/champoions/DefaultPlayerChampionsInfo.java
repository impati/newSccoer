package com.example.newscoccer.SearchService.player.info.champoions;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import com.example.newscoccer.springDataJpa.PlayerChampionsRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *  시즌 + 선수 정보로 챔피언스 리그 정보를 가져옴. 리그와 거의 비슷.
 *
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultPlayerChampionsInfo implements EntityRecordInfo<PlayerChampionsInfoRequest , PlayerChampionsInfoResponse> {
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;

    @Override
    public PlayerChampionsInfoResponse recordInfo(PlayerChampionsInfoRequest req) {
        PlayerChampionsInfoResponse resp = new PlayerChampionsInfoResponse();
        List<PlayerChampionsRecord> playerChampionsRecords = playerChampionsRecordRepository.findByPlayerAndSeason(req.getPlayerId(), req.getSeason());
        for (var pcr : playerChampionsRecords) {
            resp.setRank(pcr.getRank());
            resp.setGoal(resp.getGoal() + pcr.getGoal());
            resp.setAssist(resp.getAssist() + pcr.getAssist());
            resp.setPass(resp.getPass() + pcr.getPass());
            resp.setShooting(resp.getShooting() + pcr.getShooting());
            resp.setValidShooting(resp.getValidShooting() + pcr.getValidShooting());
            resp.setDefense(resp.getDefense() + pcr.getGoodDefense());
            resp.setFoul(resp.getFoul() + pcr.getFoul());
            resp.setAvgGrade(resp.getAvgGrade() + pcr.getGrade());
            resp.setIsBest(resp.getIsBest() + (pcr.isBest() ? 1 : 0));
        }
        int sz = playerChampionsRecords.size();
        if(sz == 0) return resp;
        else {
            resp.setGameNumber(sz);
            resp.setAvgGrade(Math.round((resp.getAvgGrade()/sz)*100) / 100.0);
            MatchResultUtils matchResultUtils = new MatchResultUtils(playerChampionsRecords);
            resp.setWin(matchResultUtils.getWin());
            resp.setDraw(matchResultUtils.getDraw());
            resp.setLose(matchResultUtils.getLose());
            return resp;
        }
    }
}
