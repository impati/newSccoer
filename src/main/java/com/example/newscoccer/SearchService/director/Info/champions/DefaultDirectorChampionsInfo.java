package com.example.newscoccer.SearchService.director.Info.champions;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.record.MatchResultUtils;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultDirectorChampionsInfo implements EntityRecordInfo<DirectorChampionsInfoRequest,DirectorChampionsInfoResponse> {
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;

    /**
     *  1. 시즌 챔피언스리그 감독의   팀순위 , 승 무 패 정보를 세팅.
     *  2. 시즌 챔피언스리그 감독의 상대편과 대결 정보를 가져옴
     */
    @Override
    public DirectorChampionsInfoResponse recordInfo(DirectorChampionsInfoRequest req) {
        int rank = 0 , win = 0 , draw = 0 , lose = 0 ;

        /** 1 */
        List<TeamChampionsRecord> teamChampionsRecords = teamChampionsRecordRepository.findByDirector(req.getDirectorId(),req.getSeason());
        MatchResultUtils matchResultUtils  = new MatchResultUtils(teamChampionsRecords);
        for (TeamChampionsRecord record : teamChampionsRecords) {
            rank = record.getRank();
        }
        win = matchResultUtils.getWin();
        draw = matchResultUtils.getDraw();
        lose = matchResultUtils.getLose();
        DirectorChampionsInfoResponse resp = new DirectorChampionsInfoResponse(rank,win , draw , lose);

        /** 2 */
        teamChampionsRecordRepository.findRoundByDirector(req.getDirectorId(),req.getSeason()).stream().forEach(ele->{
            addRecord(req.getDirectorId(),resp,ele , teamChampionsRecordRepository.findByRound(ele));
        });

        return resp;
    }

    /**
     *
     * @param directorId 현재 감독
     * @param resp resp 에 정보를 넣어야함
     * @param round 라운드가 끝났는지 정보를 넣어주기 위해
     * @param roundRecord  메인 정보
     */
    private void addRecord(Long directorId , DirectorChampionsInfoResponse resp , Round round , List<TeamChampionsRecord> roundRecord){
        TeamChampionsRecord i = roundRecord.stream().
                filter(ele -> ele.getDirector().getId().equals(directorId))
                .findFirst().orElse(null);
        TeamChampionsRecord opposite = roundRecord.stream().
                filter(ele -> !ele.getDirector().getId().equals(directorId))
                .findFirst().orElse(null);
        resp.addRecord(round , i.getTeam().getName(),opposite.getTeam().getName(),i.getScore(), opposite.getScore());
    }
}
