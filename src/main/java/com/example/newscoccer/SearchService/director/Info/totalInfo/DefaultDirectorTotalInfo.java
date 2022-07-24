package com.example.newscoccer.SearchService.director.Info.totalInfo;

import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *  감독의 전체 기록 기능 .
 *  1. 전체 승 , 무 , 패
 *  2. 리그 순위
 *  3. 챔피언스 리그 순위
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DefaultDirectorTotalInfo implements EntityTotalInfo<DirectorTotalInfoRequest , DirectorTotalInfoResponse> {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final DirectorRepository directorRepository;
    @Override
    public DirectorTotalInfoResponse totalInfo(DirectorTotalInfoRequest req) {
        DirectorTotalInfoResponse resp = new DirectorTotalInfoResponse();
        Director director = directorRepository.findById(req.getDirectorId()).orElse(null);
        leagueTakeCareOf(resp,director);
        championsTakeCareOf(resp,director);
        return resp;
    }

    /**
     * 리그 정보 승,무,패, 순위
     */
    private void leagueTakeCareOf(DirectorTotalInfoResponse resp , Director director){
        MatchResultUtils matchResultUtils = new MatchResultUtils(teamLeagueRecordRepository.findByDirector(director));
        resp.setWin(resp.getWin() + matchResultUtils.getWin());
        resp.setDraw(resp.getDraw() + matchResultUtils.getDraw());
        resp.setLose(resp.getLose() + matchResultUtils.getLose());
        resp.calcRank(teamLeagueRecordRepository.findBySeasonLastGame(director),TeamLeagueRecord.class);
    }

    /**
     * 챔피언스리그 승,무,패, 순위
     */
    private void championsTakeCareOf(DirectorTotalInfoResponse resp , Director director){
        MatchResultUtils matchResultUtils = new MatchResultUtils(teamChampionsRecordRepository.findByDirector(director));
        resp.setWin(resp.getWin() + matchResultUtils.getWin());
        resp.setDraw(resp.getDraw() + matchResultUtils.getDraw());
        resp.setLose(resp.getLose() + matchResultUtils.getLose());

        /**
         * 팀의 챔피언스리그 시즌 전체 기록을 가져옴 .
         * calcRank()는 시즌 단위로 처리하므로
         * 전체 시즌을 넘겨줘야함 .
         */

        for(int i = 0;i <= SeasonUtils.currentSeason;i++){
            resp.calcRank(teamChampionsRecordRepository.findByDirector(director.getId(),i),TeamChampionsRecord.class);
        }

    }
}
