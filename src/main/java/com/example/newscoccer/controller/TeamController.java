package com.example.newscoccer.controller;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.SearchService.team.info.TeamSimpleInfoRequest;
import com.example.newscoccer.SearchService.team.info.TeamSimpleInfoResponse;
import com.example.newscoccer.SearchService.team.info.champions.TeamChampionsInfoRequest;
import com.example.newscoccer.SearchService.team.info.champions.TeamChampionsInfoResponse;
import com.example.newscoccer.SearchService.team.info.league.TeamLeagueInfoRequest;
import com.example.newscoccer.SearchService.team.info.league.TeamLeagueInfoResponse;
import com.example.newscoccer.SearchService.team.info.totalInfo.TeamTotalInfoRequest;
import com.example.newscoccer.SearchService.team.info.totalInfo.TeamTotalInfoResponse;
import com.example.newscoccer.domain.SeasonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GET /team/{teamId} ->팀 페이지
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final EntitySimpleInfo<TeamSimpleInfoRequest , TeamSimpleInfoResponse > simpleInfo;
    private final EntityRecordInfo<TeamLeagueInfoRequest , TeamLeagueInfoResponse> leagueInfo;
    private final EntityRecordInfo<TeamChampionsInfoRequest , TeamChampionsInfoResponse> championsInfo;
    private final EntityTotalInfo <TeamTotalInfoRequest, TeamTotalInfoResponse> totalInfo;

    @GetMapping("/{teamId}")
    public String teamPage(@PathVariable Long teamId,@RequestParam(required = false) Integer season, Model model){
        if(season == null) season = SeasonUtils.currentSeason;
        model.addAttribute("Seasons", SeasonUtils.currentSeason);
        model.addAttribute("teamId",teamId);
        model.addAttribute("simpleResp",simpleInfo.simpleInfo(new TeamSimpleInfoRequest(teamId)));
        model.addAttribute("leagueResp",leagueInfo.recordInfo(new TeamLeagueInfoRequest(teamId,season)));
        model.addAttribute("championsResp",championsInfo.recordInfo(new TeamChampionsInfoRequest(teamId,season)));
        model.addAttribute("totalResp",totalInfo.totalInfo(new TeamTotalInfoRequest(teamId)));
        return "/teamPage";
    }
}
