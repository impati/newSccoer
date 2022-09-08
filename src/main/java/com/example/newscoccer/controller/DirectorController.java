package com.example.newscoccer.controller;

import com.example.newscoccer.RegisterService.director.DirectorUpdate;
import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.SearchService.director.Info.DirectorSimpleInfoRequest;
import com.example.newscoccer.SearchService.director.Info.DirectorSimpleInfoResponse;
import com.example.newscoccer.SearchService.director.Info.champions.DirectorChampionsInfoRequest;
import com.example.newscoccer.SearchService.director.Info.champions.DirectorChampionsInfoResponse;
import com.example.newscoccer.SearchService.director.Info.league.DirectorLeagueInfoRequest;
import com.example.newscoccer.SearchService.director.Info.league.DirectorLeagueInfoResponse;
import com.example.newscoccer.SearchService.director.Info.totalInfo.DirectorTotalInfoRequest;
import com.example.newscoccer.SearchService.director.Info.totalInfo.DirectorTotalInfoResponse;
import com.example.newscoccer.SearchService.director.search.DirectorSearch;
import com.example.newscoccer.SearchService.director.search.DirectorSearchRequest;
import com.example.newscoccer.controller.form.DirectorForm;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.exception.NotFoundEntity;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Get /director/register -> 감독 등록 페이지
 * Post /director/register -> 감독 등록
 *
 * Get /director/directors -> 감독 전체 목록
 *
 * Get /director/{id}?season= ->감독 페이지
 *
 * Get /director/edit/{id} ->감독 수정 페이지
 * Post /director/edit ->선수 수정
 *
 *
 *
 *
 * Director team 이 null 일 수 있다.
 */
@Slf4j
@RequestMapping("/director")
@Controller
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;
    private final DirectorUpdate directorUpdate;
    private final DirectorSearch directorSearch;
    private final LeagueRepository leagueRepository;
    private final EntitySimpleInfo<DirectorSimpleInfoRequest , DirectorSimpleInfoResponse> simpleInfo;
    private final EntityRecordInfo<DirectorLeagueInfoRequest , DirectorLeagueInfoResponse> leagueInfo;
    private final EntityRecordInfo<DirectorChampionsInfoRequest, DirectorChampionsInfoResponse> championsInfo;
    private final EntityTotalInfo<DirectorTotalInfoRequest, DirectorTotalInfoResponse> totalInfo;
    /**
     * 감독 등록 페이지
     */
    @GetMapping("/register")
    public String directorRegisterPage(Model model){
        model.addAttribute("teamList",getTeamList());
        model.addAttribute("directorForm",new DirectorForm());
        return "director/register";
    }
    /**
     * 감독 등록
     */
    @PostMapping("/register")
    public String directorRegister(@Validated  @ModelAttribute DirectorForm directorForm,
                                   BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("teamList",getTeamList());
            return "director/register";
        }
        directorUpdate.directorSave(directorForm.getDirectorName(),directorForm.getTeamId());
        return "redirect:/director/directors";
    }

    /**
     * 팀 전체 리스트와
     * {
     *      리그 : null ,
     *      id : null
     * }
     * 인 팀을 저장한 후 리턴
     * @return
     */
    private List<Team> getTeamList(){
        List<Team> teamList = teamRepository.findAll();
        Team team = Team.createTeam(null,"팀 없음");
        team.setId(null);
        teamList.add(team);
        return teamList;
    }


    @GetMapping("/directors")
    public String directorList(@ModelAttribute DirectorSearchRequest directorSearchRequest ,Model model){
        model.addAttribute("leagueList",leagueRepository.findAll());
        if(directorSearchRequest.getLeagueId() != null)
            model.addAttribute("teamList",teamRepository.findByLeague(directorSearchRequest.getLeagueId()));
        model.addAttribute("directSearchResponse",directorSearch.directorSearch(directorSearchRequest));
        return "director/directorList";
    }

    @GetMapping("/{directorId}")
    public String directorPage(@PathVariable Long directorId , @RequestParam(required = false) Integer season,Model model){
        if(season == null) season = SeasonUtils.currentSeason;
        model.addAttribute("Seasons",SeasonUtils.currentSeason);
        model.addAttribute("directorId",directorId);
        // 기본적인 팀정보.

        model.addAttribute("simpleInfoResponse",simpleInfo.simpleInfo(new DirectorSimpleInfoRequest(directorId)));
        model.addAttribute("leagueInfoResponse",leagueInfo.recordInfo(new DirectorLeagueInfoRequest(directorId, season)));
        model.addAttribute("championsInfoResponse", championsInfo.recordInfo(new DirectorChampionsInfoRequest(directorId, season)));
        model.addAttribute("totalInfoResponse" , totalInfo.totalInfo(new DirectorTotalInfoRequest(directorId)));
        return "director/page";
    }


    @GetMapping("/edit/{directorId}")
    public String directorEditPage(@PathVariable Long directorId  , Model model){
        Director director = directorRepository.findById(directorId).orElse(null);
        if(director == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"감독이 없습니다",new NotFoundEntity("감독이 없음"));

        DirectorForm directorForm = new DirectorForm();
        directorForm.setDirectorName(director.getName());
        if(director.getTeam()!=null)directorForm.setTeamId(director.getTeam().getId());

        model.addAttribute("directorForm",directorForm);

        model.addAttribute("teamList",getTeamList());
        return "director/edit";
    }

    @PostMapping("/edit/{directorId}")
    public String directorEdit(@PathVariable Long directorId,@Validated @ModelAttribute DirectorForm directorForm,BindingResult bindingResult , Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("teamList",getTeamList());
            return "director/edit";
        }
        directorUpdate.directorEdit(directorId,directorForm.getDirectorName(),directorForm.getTeamId());
        return "redirect:/director/edit/"+directorId;
    }








}
