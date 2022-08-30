package com.example.newscoccer.controller;

import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpRegister;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResult;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResultDto;
import com.example.newscoccer.SearchService.round.championsRound.ChampionsRoundInfo;
import com.example.newscoccer.SearchService.round.championsRound.ChampionsRoundInfoRequest;
import com.example.newscoccer.SearchService.round.common.faceToHead.*;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUp;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpRequest;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpResponse;
import com.example.newscoccer.SearchService.round.leagueRound.LeagueRoundInfo;
import com.example.newscoccer.SearchService.round.leagueRound.LeagueRoundInfoRequest;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *  Get round/league -> 리그 라운드 정보 , 4개 리그를 모두 보여줌 .
 *  Get round/champions ->챔피언스 라운드 정보
 *
 *  Get ,POST round/league/{roundId}/line-up,round/champions/{roundId}/line-up -> 라운드 공통 처리
 *
 *  GET /round/power/{roundId} -> 상대 전력 페이지
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/round")
public class RoundController {

    private final LeagueRoundInfo leagueRoundInfo;
    private final LeagueRepository leagueRepository;
    private final ChampionsRoundInfo championsRoundInfo;
    private final RoundLineUp roundLineUp;
    private final LineUpRegister lineUpRegister;
    private final FaceToHead faceToHead;
    @GetMapping("/league")
    public String leagueRound(@RequestParam(required = false) Integer season,
                              @RequestParam(required = false) Integer roundSt,
                              Model model){
        if(season == null) season = SeasonUtils.currentSeason;
        if(roundSt == null) roundSt = SeasonUtils.currentLeagueRoundSt;
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("Seasons",SeasonUtils.currentSeason);
        model.addAttribute("lastRound", SeasonUtils.currentLeagueRoundSt);
        model.addAttribute("currentRound", SeasonUtils.currentLeagueRoundSt);


        List<League> leagueList = leagueRepository.findAll();

        model.addAttribute("GLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(0).getId(),season,roundSt)).getRoundInfoDtoList());

        model.addAttribute("LLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(0).getId(),season,roundSt)).getRoundInfoDtoList());
        model.addAttribute("ELeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(0).getId(),season,roundSt)).getRoundInfoDtoList());
        model.addAttribute("SLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(0).getId(),season,roundSt)).getRoundInfoDtoList());
        return "round/league";
    }


    @GetMapping("/champions")
    public String championsRound(@RequestParam(required = false)Integer season,
                                 @RequestParam(required = false)Integer roundSt,
                                 Model model){

        if(season == null) season = SeasonUtils.currentSeason;
        if(roundSt == null) roundSt = SeasonUtils.currentChampionsRoundSt;
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("season",season);
        model.addAttribute("Seasons",SeasonUtils.currentSeason);

        model.addAttribute("ChampionsRoundInfoFirst",championsRoundInfo.championsRoundInformation(new ChampionsRoundInfoRequest(season, roundSt)).getFirstRound());
        model.addAttribute("ChampionsRoundInfoSecond",championsRoundInfo.championsRoundInformation(new ChampionsRoundInfoRequest(season, roundSt)).getSecondRound());


        return "round/champions";
    }


    /**
     * 라인업 페이지
     * TODO : 챔피언스 결승전일 경우 뷰를 다르게 내려주어야함.
     */
    @GetMapping(value = {"/league/{roundId}/line-up","/champions/{roundId}/line-up"})
    public String lineUpPage(@PathVariable Long roundId ,HttpServletRequest req,Model model){
        if(req.getRequestURI().contains("league")) {
            model.addAttribute("type","league");
            log.info("league");
        }
        else model.addAttribute("type","champions");


        model.addAttribute("roundId",roundId);
        model.addAttribute("positionList", Position.values());
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));
        model.addAttribute("roundLineUpResponse",resp);
        model.addAttribute("newData",new NewData(resp));
        return "round/lineUp";
    }


    /**
     * 라인업 저장
     * @return
     */
    @PostMapping(value = {"/league/{roundId}/line-up","/champions/{roundId}/line-up"})
    public String lineUp(@PathVariable Long roundId,@ModelAttribute NewData newData){
         NewData original = new NewData(roundLineUp.lineUp(new RoundLineUpRequest(roundId)));
        LineUpResultDto lineUpResultDto = new LineUpResultDto();
        lineUpResultDto.setRoundId(roundId);

        newData.getJoinPlayer().stream().forEach(p->{
            int index = original.getJoinPlayer().indexOf(p);
            Position position = newData.getJoinPosition().get(index);
            lineUpResultDto.getParticipatePlayer().add( new LineUpResult(p,position));
        });

        lineUpRegister.lineUpRegister(lineUpResultDto);
         return "redirect:";
    }


    /**
     *
     * 상대 전력 페이지
     * TODO : 데이터가 많이 들어간 후에 유효성 검증
     */
    @GetMapping("/power/{roundId}")
    public String powerPage(@PathVariable Long roundId, Model model){

        ComparisonRecordResponse comparisonResp = faceToHead.comparison(roundId);
        model.addAttribute("comparisonA",comparisonResp.getRecordList().get(0));
        model.addAttribute("comparisonB",comparisonResp.getRecordList().get(1));

        RecentRecordResponse recentRecordResponse = faceToHead.recentRecord(roundId);
        model.addAttribute("recentShowDown",recentRecordResponse.getSimpleRecordResultDtoList());


        TopPlayerResponse topPlayerResponse = faceToHead.top5Player(roundId);
        model.addAttribute("teamATopPlayer",topPlayerResponse.getTeamAPlayerList());
        model.addAttribute("teamBTopPlayer",topPlayerResponse.getTeamBPlayerList());


        TotalComparisonRecordResponse totalComparisonRecordResponse = faceToHead.totalComparison(roundId);
        model.addAttribute("totalShowDown",totalComparisonRecordResponse);


        return "round/power";
    }


















    @Data
    @NoArgsConstructor
    static class NewData{
        List<Position> joinPosition = new ArrayList<>();
        List<Long> joinPlayer = new ArrayList<>();

        public NewData(RoundLineUpResponse resp) {
            resp.getPlayerListA().stream().forEach(p->{
                joinPlayer.add(p.getPlayerId());
            });
            resp.getPlayerListB().stream().forEach(p->{
                joinPlayer.add(p.getPlayerId());
            });
        }
    }

}
