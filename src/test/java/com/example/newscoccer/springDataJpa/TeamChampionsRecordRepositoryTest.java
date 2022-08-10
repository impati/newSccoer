package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.support.RandomNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SpringBootTest
@Transactional
class TeamChampionsRecordRepositoryTest {
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Test
    void findTeamScoreTest() {
        Director director = directorRepository.findById(1L).orElse(null);
        List<Round> roundList = teamChampionsRecordRepository.findRoundByDirector(director.getId(),0);
        Assertions.assertThat(roundList.stream().count()).isEqualTo(2);

        for (Round round : roundList) {
            Assertions.assertThat(teamChampionsRecordRepository.findByRound(round).stream().count()).isEqualTo(2);
        }
    }
    @Test
    void findByDirector(){
        Assertions.assertThat(teamChampionsRecordRepository.findByDirector(1L,0).stream().count()).isEqualTo(2L);
    }
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RoundRepository roundRepository;

    @Test
    @DisplayName(" findTeamBySeason" )
    public void findTeamBySeason() throws Exception{
        // given
        // given
        League league1 = new League("league1");
        League league2 = new League("league2");
        leagueRepository.save(league1);
        leagueRepository.save(league2);

        Team team11 = Team.createTeam(league1,"team11");
        Team team12 = Team.createTeam(league1,"team12");

        Team team21 = Team.createTeam(league2,"team21");
        Team team22 = Team.createTeam(league2,"team22");

        teamRepository.save(team11);
        teamRepository.save(team12);
        teamRepository.save(team21);
        teamRepository.save(team22);
        // when


        saveChampionsRecord(100,16,1,team11,team21);
        saveChampionsRecord(100,16,1,team12,team22);

        saveChampionsRecord(100,8,1,team11,team22);







        // then
        teamChampionsRecordRepository.findTeamBySeason(100)
                .stream().forEach(t-> System.out.println(t.getName()));


    }

    private void saveChampionsRecord(int season , int roundSt ,int fs , Team team11, Team team21) {
        ChampionsRound round = new ChampionsRound(season,roundSt,1);
        round.setRoundStatus(RoundStatus.DONE);
        roundRepository.save(round);

        TeamChampionsRecord tcr1 = TeamChampionsRecord.create(round, team11,fs);
        TeamChampionsRecord tcr2 = TeamChampionsRecord.create(round, team21,fs);

        //  경기 기록
        int scoreA = RandomNumber.returnRandomNumber(0,5);
        int scoreB = RandomNumber.returnRandomNumber(0,5);
        tcr1.setScore(scoreA);
        tcr1.setOppositeScore(scoreA);

        tcr2.setOppositeScore(scoreB);
        tcr2.setScore(scoreB);

        if(scoreA > scoreB) {
            tcr1.setMatchResult(MatchResult.WIN);
            tcr2.setMatchResult(MatchResult.LOSE);
        }
        else if(scoreA < scoreB){
            tcr1.setMatchResult(MatchResult.LOSE);
            tcr2.setMatchResult(MatchResult.WIN);
        }
        else {
            tcr1.setMatchResult(MatchResult.DRAW);
            tcr2.setMatchResult(MatchResult.DRAW);
        }

        // 저장
        teamChampionsRecordRepository.save(tcr1);
        teamChampionsRecordRepository.save(tcr2);
    }



}