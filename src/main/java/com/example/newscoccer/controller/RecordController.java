package com.example.newscoccer.controller;

import com.example.newscoccer.SearchService.record.player.SearchPlayerRecord;
import com.example.newscoccer.SearchService.record.team.SearchTeamRecord;
import com.example.newscoccer.domain.Direction;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.SortType;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final LeagueRepository leagueRepository;
    private final SearchTeamRecord searchTeamRecord;
    private final SearchPlayerRecord searchPlayerRecord;
    @GetMapping(value = {"/league/{leagueId}/team","league/team"})
    public String leagueTeamRank(@PathVariable(required = false) Long leagueId  ,
                                 @RequestParam(required = false) Integer season , Model model){
        if(leagueId == null) leagueId = 1L;
        if(season == null) season = SeasonUtils.currentSeason;
        model.addAttribute("Seasons", SeasonUtils.currentSeason);
        model.addAttribute("season",season);
        League league = leagueRepository.findById(leagueId).orElse(null);
        model.addAttribute("league",league);
        model.addAttribute("leagueTeamRecordResponse",
                searchTeamRecord.searchLeagueTeamRecord(leagueId, season).getResultList());
        return "record/league/teamRecord";
    }


    //TODO : Direction.ASC 구현
    @GetMapping(value = {"/league/player","/league/{leagueId}/player"})
    public String leaguePlayerRecord(@PathVariable(required = false) Long leagueId ,
                                     @RequestParam(required = false) Integer season ,
                                     @RequestParam(required = false) SortType sortType , Model model){
        if(leagueId == null) leagueId = 1L;
        if(season == null) season = SeasonUtils.currentSeason;
        if(sortType == null) sortType  = SortType.GOAL;
        League league = leagueRepository.findById(leagueId).orElse(null);

        model.addAttribute("Seasons", SeasonUtils.currentSeason);
        model.addAttribute("season",season);
        model.addAttribute("sortType",sortType);
        model.addAttribute("league",league);
        model.addAttribute("leaguePlayerRecordResponse",
                searchPlayerRecord.searchLeaguePlayerRecord(leagueId,season,sortType, Direction.DESC).getResultList());
        return "record/league/playerRecord";
    }

    @GetMapping("/champions/team")
    public String championsTeamRecord(@RequestParam(required = false) Integer season,Model model ){

        if(season == null) season = SeasonUtils.currentSeason;
        model.addAttribute("Seasons", SeasonUtils.currentSeason);
        model.addAttribute("season",season);
        model.addAttribute("championsTeamRecordResponse",
                searchTeamRecord.searchChampionsTeamRecord(season).getResultList());
        return "record/champions/teamRecord";
    }

    @GetMapping("/champions/player")
    public String championsPlayerRecord(@RequestParam(required = false) Integer season ,
                                        @RequestParam(required = false) SortType sortType ,
                                        Model model){

        if(season == null) season = SeasonUtils.currentSeason;
        if(sortType == null) sortType  = SortType.GOAL;

        model.addAttribute("Seasons", SeasonUtils.currentSeason);
        model.addAttribute("season",season);
        model.addAttribute("sortType",sortType);

        model.addAttribute("championsPlayerRecordResponse",
                searchPlayerRecord.searchChampionsPlayerRecord(season,sortType,Direction.DESC).getResultList());

        return "record/champions/playerRecord";
    }









}
