package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.*;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LeagueRoundGeneratorImpl implements LeagueRoundGenerator{
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;


    /**
     *
     * @param season
     * 라운드
     * 항상 4대 리그를 시즌 단위로 한번에 생성 -> teamLeagueRecord
     */
    @Override
    public void generator(int season) {
        if(roundRepository.findBySeason(season) > 0) return ;

        leagueRepository.findAll().stream().forEach(league->{
            List<Team> teamList = teamRepository.findByLeague(league);
            int idx = 0;
            while(idx < 45) {
                int teamIdList[] = new int[17];
                int matchUp[][] = new int[17][16]; // teamId : [] , roundSt : [] , value  = opposite
                boolean posible[][] = new boolean[17][17];
                for (int j = 0; j < 15; j++) {
                    boolean oppositeDone[] = new boolean[17];
                    for (int i = 0; i < 16; i++) {
                        for (int k = 0; k < 16; k++) {
                            if (i != k && !oppositeDone[k]) {
                                if ((teamIdList[i] & (1 << k)) == 0) {
                                    matchUp[i][j] = k;
                                    oppositeDone[k] = true;
                                    teamIdList[i] |= (1 << k);
                                    break;
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < 16; i++) {
                    for (int k = 0; k < 15; k++) {
                        int opposite = matchUp[i][k];
                        if (posible[i][opposite] || posible[opposite][i]) continue;
                        posible[i][opposite] = true;
                        posible[opposite][i] = true;
                        Round round = new LeagueRound(league,season, k + 1 + idx);
                        roundRepository.save(round);
                        saveEntity(round, teamList.get(i));
                        saveEntity(round, teamList.get(opposite));

                    }
                }
                idx += 15;
            }

        });
    }

    /**
     * @param round ,team
     * round 에 대한 팀 리그 레코드 생성.
     */
    private void saveEntity(Round round , Team team){
        TeamLeagueRecord teamRecord = TeamLeagueRecord.create(round,team);
        teamLeagueRecordRepository.save((TeamLeagueRecord) teamRecord);
    }
}