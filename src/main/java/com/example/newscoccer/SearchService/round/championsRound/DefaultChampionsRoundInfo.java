package com.example.newscoccer.SearchService.round.championsRound;

import com.example.newscoccer.SearchService.round.RoundInfoDto;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 시즌 + 라운드로 챔피언스 기록을 조회
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultChampionsRoundInfo implements ChampionsRoundInfo{
    private final RoundRepository roundRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Override
    public ChampionsRoundInfoResponse championsRoundInformation(ChampionsRoundInfoRequest req) {
        ChampionsRoundInfoResponse resp = new ChampionsRoundInfoResponse();

        Map<Long, RoundInfoDto> firstMapped = new HashMap<>();
        Map<Long, RoundInfoDto> secondMapped = new HashMap<>();

        List<ChampionsRound> roundList = roundRepository.findChampionsRound(req.getSeason(),req.getRoundSt());
        teamChampionsRecordRepository.findByRoundList(roundList).stream()
                .forEach(tcr->{
                    ChampionsRound round = tcr.getRound();
                    mapped(firstMapped,round,tcr,1);
                    mapped(secondMapped,round,tcr,2);
                });


        addAndSort(resp.getFirstRound(),firstMapped);
        addAndSort(resp.getSecondRound(),secondMapped);

        return resp;
    }



    private void addAndSort(List<RoundInfoDto> list,Map<Long , RoundInfoDto> mapped){
        for(var element :mapped.keySet()){
            list.add(mapped.get(element));
        }

        list.sort((t1,t2)-> {
            if(t1.getTeamA().getId() > t2.getTeamA().getId()) return 1;
            else return -1;
        });

    }



    private void mapped(Map<Long,RoundInfoDto> mapped , Round round , TeamChampionsRecord tcr,int order){
        if(tcr.getFirstOrSecond() == order) {
            if (mapped.containsKey(round.getId())) {
                RoundInfoDto dto = mapped.get(round.getId());

                dto.setTeamB(tcr.getTeam());
                dto.setScoreB(tcr.getScore());
            } else {
                RoundInfoDto dto = new RoundInfoDto();
                dto.setTeamA(tcr.getTeam());
                dto.setScoreA(tcr.getScore());
                dto.setDone(round.getRoundStatus() == RoundStatus.DONE ? true : false);
                mapped.put(round.getId(), dto);
            }
        }
    }
}
