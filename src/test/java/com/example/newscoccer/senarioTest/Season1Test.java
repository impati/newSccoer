package com.example.newscoccer.senarioTest;

import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.SearchService.player.info.totalInfo.PlayerTotalInfoRequest;
import com.example.newscoccer.SearchService.player.info.totalInfo.PlayerTotalInfoResponse;
import com.example.newscoccer.SearchService.record.player.SearchPlayerRecord;
import com.example.newscoccer.SearchService.record.team.SearchTeamRecord;
import com.example.newscoccer.SearchService.record.team.SearchTeamRecordResponse;
import com.example.newscoccer.SearchService.team.info.totalInfo.TeamTotalInfoRequest;
import com.example.newscoccer.SearchService.team.info.totalInfo.TeamTotalInfoResponse;
import com.example.newscoccer.auto.Game.AutoGameRegister;
import com.example.newscoccer.auto.lineUp.AutoLineUp;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class Season1Test {

    /**
     * PostData 이후
     * 라운드 저장 -> 경기 완료 -> 골 - 어시 저장 싸이클
     */

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    AutoLineUp autoLineUp;

    @Autowired
    AutoGameRegister autoGameRegister;


    @Autowired
    SearchTeamRecord searchTeamRecord;
    @Autowired
    SearchPlayerRecord searchPlayerRecord;

    @Test
    @DisplayName("리그 0시즌 1라운드를 수행해본다 ")
    public void leagueRound1() throws Exception{
        // given
        // when
        roundRepository.findLeagueRound(0,1).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });
        // then
        // 기록을 조회
        SearchTeamRecordResponse resp = searchTeamRecord.searchLeagueTeamRecord(1L,0);
        resp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());
                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.print(" diff = " + ele.getDiff());
                    System.out.println(" gameNumber = " + ele.getGameNumber());
                });

    }

//    @Test
    @DisplayName("리그 0시즌을 모두 수행해본다 ")
    public void leagueRoundAll() throws Exception{
        // given
        // when
        for(int i = 1; i<= 45;i++) {
            roundRepository.findLeagueRound(0, i).stream()
                    .forEach(r -> {
                        //라인업을 모두 저장함.
                        autoLineUp.autoLineUp(r.getId());
                        //1라운드를 모두 수행.
                        autoGameRegister.autoGameRegister(r.getId());
                    });
            System.out.println("complete = " + i);
        }
        // then
        Assertions.assertThat(SeasonUtils.currentSeason).isEqualTo(0);
        Assertions.assertThat(SeasonUtils.currentLeagueRoundSt).isEqualTo(SeasonUtils.lastLeagueRoundSt);


        System.out.println("=============");
        SearchTeamRecordResponse resp = searchTeamRecord.searchLeagueTeamRecord(1L,0);
        resp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());
                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.print(" diff = " + ele.getDiff());
                    System.out.println(" gameNumber = " + ele.getGameNumber());
                });
    }



    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Test
    @DisplayName("챔피언스리그 0 시즌 1라운드 실행")
    public void champions() throws Exception{
        // given
        roundRepository.findChampionsRound(0,16).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });

        // when
        // then
        SearchTeamRecordResponse resp = searchTeamRecord.searchChampionsTeamRecord(0);
        resp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());

                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.println(" diff = " + ele.getDiff());
                });
        teamChampionsRecordRepository.findBySeasonAndRoundSt(0,8)
                .stream()
                .forEach(tcr-> System.out.println("teamName = " + tcr.getTeam().getName()));



    }


    @Test
    @DisplayName("챔피언스 리그 전부 실행")
    public void championsAll() throws Exception{

        roundRepository.findChampionsRound(0,16).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });
        Assertions.assertThat(SeasonUtils.currentChampionsRoundSt).isEqualTo(8);
        roundRepository.findChampionsRound(0,8).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });
        Assertions.assertThat(SeasonUtils.currentChampionsRoundSt).isEqualTo(4);
        roundRepository.findChampionsRound(0,4).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });
        Assertions.assertThat(SeasonUtils.currentChampionsRoundSt).isEqualTo(2);
        roundRepository.findChampionsRound(0,2).stream()
                .forEach(r->{
                    //라인업을 모두 저장함.
                    autoLineUp.autoLineUp(r.getId());
                    //1라운드를 모두 수행.
                    autoGameRegister.autoGameRegister(r.getId());
                });



        SearchTeamRecordResponse resp = searchTeamRecord.searchChampionsTeamRecord(0);
        resp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());

                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.println(" diff = " + ele.getDiff());
                });
    }




//    @Test
    @DisplayName("0 시즌 전부 끝")
    public void seasonALl() throws Exception{
        // given
        // when
        leagueRoundAll();
        championsAll();


        Assertions.assertThat(SeasonUtils.currentSeason).isEqualTo(1);
        Assertions.assertThat(SeasonUtils.currentChampionsRoundSt).isEqualTo(16);
        Assertions.assertThat(SeasonUtils.currentLeagueRoundSt).isEqualTo(1);

        Assertions.assertThat(roundRepository.findBySeason(1)).isEqualTo(32 *45);
        Assertions.assertThat(roundRepository.findChampionsRound(1,16).size()).isEqualTo(16);


        // then

    }


    @Autowired
    EntityTotalInfo<TeamTotalInfoRequest, TeamTotalInfoResponse> teamTotalInfo;
    @Autowired
    EntityTotalInfo<PlayerTotalInfoRequest, PlayerTotalInfoResponse> playerTotalInfo;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;

//    @Test
    @DisplayName("0시즌 끝 순위 결과 ")
    public void seasonRank() throws Exception{
        // given
        leagueRoundAll();
        championsAll();
        // when
        // then
        SearchTeamRecordResponse leagueResp = searchTeamRecord.searchLeagueTeamRecord(1L, 0);
        leagueResp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());
                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.print(" diff = " + ele.getDiff());
                    System.out.println(" gameNumber = " + ele.getGameNumber());
                });


        SearchTeamRecordResponse championsResp = searchTeamRecord.searchChampionsTeamRecord(0);
        championsResp.getResultList()
                .stream()
                .forEach(ele->{
                    System.out.print(" teamName = " + ele.getTeamName());

                    System.out.print(" rank = " + ele.getRank());
                    System.out.print(" win = " + ele.getWin());
                    System.out.print(" draw = " + ele.getDraw());
                    System.out.print(" lose = " + ele.getLose());
                    System.out.print(" gain = " + ele.getGain());
                    System.out.print(" lost = " + ele.getLost());
                    System.out.println(" diff = " + ele.getDiff());
                });

        for(Long i = 1L; i<=16L;i++){
            TeamTotalInfoResponse teams = teamTotalInfo.totalInfo(new TeamTotalInfoRequest(i));
            System.out.println("teams = " + teams);
            Optional<Team> team = teamRepository.findById(i);
            playerRepository.findByTeam(team.get()).stream().forEach(ele->{
                PlayerTotalInfoResponse playerInfo = playerTotalInfo.totalInfo(new PlayerTotalInfoRequest(ele.getId()));
                System.out.println("playerInfo = " + playerInfo);
            });

        }



    }






}
