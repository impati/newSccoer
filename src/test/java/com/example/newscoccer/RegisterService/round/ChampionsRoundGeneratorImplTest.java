package com.example.newscoccer.RegisterService.round;

import com.example.newscoccer.NewScoccerApplication;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.springDataJpa.dto.TeamScoreDto;
import com.example.newscoccer.support.PostData;
import com.example.newscoccer.support.RandomNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@SpringBootTest
@Transactional
class ChampionsRoundGeneratorImplTest {

    @Autowired
    ChampionsRoundGenerator championsRoundGenerator;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Test
    void firstTeamList(){

        List<Team> ret = new ArrayList<>();
        ret.add(teamRepository.findByName("바이에른 뮌헨").orElse(null));
        ret.add(teamRepository.findByName("도르트문트").orElse(null));
        ret.add(teamRepository.findByName("프랑크부르크").orElse(null));
        ret.add(teamRepository.findByName("라이프치히").orElse(null));

        ret.add(teamRepository.findByName("파리 생제르맹").orElse(null));
        ret.add(teamRepository.findByName("유벤투스").orElse(null));
        ret.add(teamRepository.findByName("밀란").orElse(null));
        ret.add(teamRepository.findByName("나폴리").orElse(null));

        ret.add(teamRepository.findByName("맨체스터 시티").orElse(null));
        ret.add(teamRepository.findByName("첼시").orElse(null));
        ret.add(teamRepository.findByName("맨체스터 유나이티드").orElse(null));
        ret.add(teamRepository.findByName("리버풀").orElse(null));

        ret.add(teamRepository.findByName("세비야").orElse(null));
        ret.add(teamRepository.findByName("바르셀로나").orElse(null));
        ret.add(teamRepository.findByName("아틀레티코 마드리드").orElse(null));
        ret.add(teamRepository.findByName("레알 마드리드").orElse(null));

        ret.stream().forEach(ele->{
            Assertions.assertThat(ele).isNotNull();
        });

    }

    @Test
    @DisplayName("처음 16강 생성 테스트")
    void firstTeamTest(){
        seasonRoundStGenerator(0,16);
    }



    @Test
    @DisplayName("8 강 테스트")
    void nextRoundTest8(){
        seasonRoundStGenerator(0,16);
        CurrentRecordResult(0,16);
        seasonRoundStGenerator(0,8);
    }

    @Test
    @DisplayName("4 강 테스트")
    void nextRoundTest4(){
        seasonRoundStGenerator(0,16);
        CurrentRecordResult(0,16);
        seasonRoundStGenerator(0,8);
        CurrentRecordResult(0,8);
        seasonRoundStGenerator(0,4);
    }



    @Test
    @DisplayName("결승")
    void finalTest(){

        seasonRoundStGenerator(0,16);
        CurrentRecordResult(0,16);

        seasonRoundStGenerator(0,8);
        CurrentRecordResult(0,8);

        seasonRoundStGenerator(0,4);
        CurrentRecordResult(0,4);

        seasonRoundStGenerator(0,2);

    }




    @Test
    @DisplayName("1시즌 전체 테스트")
    void testTest(){
        List<League> leagueList = leagueRepository.findAll();
        for (League league : leagueList) {
            boolean visited [] = new boolean[17];
            List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository.findBySeasonTopN(league, 0, PageRequest.of(0, 16));
            for (TeamLeagueRecord teamLeagueRecord : teamLeagueRecordList) {
                while(true) {
                    int r = RandomNumber.returnRandomNumber(1,16);
                    if(!visited[r]){
                        teamLeagueRecord.setRank(r);
                        visited[r] = true;

                        break;
                    }
                }
            }
        }
        seasonRoundStGenerator(1,16);
        CurrentRecordResult(1,16);

        seasonRoundStGenerator(1,8);
        CurrentRecordResult(1,8);

        seasonRoundStGenerator(1,4);
        CurrentRecordResult(1,4);

        seasonRoundStGenerator(1,2);
    }


    /**
     *  season , RoundSt generator
     */
    private void seasonRoundStGenerator(int season , int roundSt){
        int checkedRoundCount = 0, firstAndSecondCount = 0;
        if(roundSt == 2)  firstAndSecondCount  = 1;
        else firstAndSecondCount  = 2;
        checkedRoundCount = (roundSt / 2)  * firstAndSecondCount;


        championsRoundGenerator.generator(season,roundSt);
        List<ChampionsRound> championsRoundList = roundRepository.findChampionsRound(season,roundSt);
        Assertions.assertThat(championsRoundList.size()).isEqualTo(checkedRoundCount);

        int start = roundSt / 2 , end = roundSt - 1;
        for (ChampionsRound championsRound : championsRoundList) {
            List<TeamChampionsRecord> records = teamChampionsRecordRepository.findByRound(championsRound);
            Assertions.assertThat(records.size()).isEqualTo(2);
            Assertions.assertThat(records.get(0).getRound().getIndex()).isEqualTo(records.get(1).getRound().getIndex());

        }

        for(int i = start ; i<=end ;i++){
            List<TeamChampionsRecord> records = teamChampionsRecordRepository.findByRoundStResults(season, roundSt, i);
            Assertions.assertThat(records.size()).isEqualTo(2 * firstAndSecondCount);
            HashMap<Team , Integer> s = new HashMap<>();
            for(var ele : records){
                if(s.get(ele.getTeam()) == null){
                    s.put(ele.getTeam(),  1);
                }
                else {
                    s.put(ele.getTeam(), s.get(ele.getTeam()) + 1);
                }
            }
            Assertions.assertThat(s.size()).isEqualTo(2);
            for (Team team : s.keySet()) {
                Assertions.assertThat(s.get(team)).isEqualTo(firstAndSecondCount);
            }
        }
    }

    /**
     *
     * season , roundSt 챔피언스경기를 끝냄.
     *
     */
    private void CurrentRecordResult(int season , int roundSt){
        int start = roundSt / 2 ,end = roundSt - 1;
        for(int i = start ; i<=end ;i++){
            teamChampionsRecordRepository.findByRoundStResults(season, roundSt, i)
                    .stream()
                    .forEach(ele->{
                        ele.setScore(RandomNumber.returnRandomNumber(0,3));
                    });
        }
    }













}