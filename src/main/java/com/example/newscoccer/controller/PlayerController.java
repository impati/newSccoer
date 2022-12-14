package com.example.newscoccer.controller;

import com.example.newscoccer.RegisterService.player.PlayerUpdate;
import com.example.newscoccer.RegisterService.player.PlayerUpdateDto;
import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.SearchService.common.EntityTotalInfo;
import com.example.newscoccer.SearchService.player.info.PlayerSimpleInfoRequest;
import com.example.newscoccer.SearchService.player.info.PlayerSimpleInfoResponse;
import com.example.newscoccer.SearchService.player.info.champoions.PlayerChampionsInfoRequest;
import com.example.newscoccer.SearchService.player.info.champoions.PlayerChampionsInfoResponse;
import com.example.newscoccer.SearchService.player.info.league.PlayerLeagueInfoRequest;
import com.example.newscoccer.SearchService.player.info.league.PlayerLeagueInfoResponse;
import com.example.newscoccer.SearchService.player.info.totalInfo.PlayerTotalInfoRequest;
import com.example.newscoccer.SearchService.player.info.totalInfo.PlayerTotalInfoResponse;
import com.example.newscoccer.SearchService.player.search.PlayerSearch;
import com.example.newscoccer.SearchService.player.search.PlayerSearchRequest;
import com.example.newscoccer.controller.validator.NotFoundValidation;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Get  /player/register -> ?????? ?????? ?????????
 *  Post /player/register -> ?????? ??????
 *
 *  Get /player/players ->?????? ?????? ??????
 *
 *  Get /player/{id}?season= -> ?????? ?????????
 *
 *  Get /player/edit/{id} ->?????? ?????? ?????????
 *  Post /player/edit/{id} -> ?????? ??????
 *
 */
@Slf4j
@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerUpdate playerUpdate;
    private final PlayerSearch playerSearch;
    private final LeagueRepository leagueRepository;
    private final EntityTotalInfo<PlayerTotalInfoRequest, PlayerTotalInfoResponse> totalInfo;
    private final EntitySimpleInfo<PlayerSimpleInfoRequest , PlayerSimpleInfoResponse> simpleInfo;
    private final EntityRecordInfo<PlayerLeagueInfoRequest, PlayerLeagueInfoResponse> leagueInfo;
    private final EntityRecordInfo<PlayerChampionsInfoRequest, PlayerChampionsInfoResponse> championsInfo;


    /**
     * ?????? ?????? ?????????
     */
    @GetMapping("/register")
    public String playerRegisterPage(Model model){
        // need
        List<Team> teamList  = teamRepository.findAll();
        Position[] positionList = Position.values();

        model.addAttribute("teamList",teamList);
        model.addAttribute("positionList",positionList);
        model.addAttribute("playerUpdateDto",new PlayerUpdateDto());
        return "player/register";
    }

    /**
     * ?????? ??????
     */
    @PostMapping("/register")
    public String playerRegister(@Validated @ModelAttribute PlayerUpdateDto playerUpdateDto,
                                 BindingResult bindingResult , Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("teamList",teamRepository.findAll());
            model.addAttribute("positionList",Position.values());
            bindingResult.reject(null,"???????????? 0 ?????? 120 ?????? ????????????.");
            return "player/register";
        }
        playerUpdate.registerPlayer(playerUpdateDto);
        return "redirect:/player/players";
    }

    /**
     * ?????? ??????
     *
     */
    @GetMapping("/players")
    public String playerList(@ModelAttribute PlayerSearchRequest playerSearchRequest, Model model){

        model.addAttribute("PositionTypes",Position.values());
        model.addAttribute("leagueList",leagueRepository.findAll());
        if(playerSearchRequest.getLeagueId() != null)
            model.addAttribute("teams",teamRepository.findByLeague(playerSearchRequest.getLeagueId()));

        model.addAttribute("playerSearchResponse",playerSearch.playerSearch(playerSearchRequest));
        return "player/players";
    }


    /**
     * ?????? ????????? .
     * @param playerId
     * @param season
     * @param model
     * @return
     */
    @NotFoundValidation
    @GetMapping("/{playerId}")
    public String playerPage(@PathVariable Long playerId ,@RequestParam(required = false) Integer season, Model model){
        if(season == null) season = SeasonUtils.currentSeason;
        model.addAttribute("Seasons",SeasonUtils.currentSeason);
        model.addAttribute("playerId",playerId);

        model.addAttribute("simpleResp",simpleInfo.simpleInfo(new PlayerSimpleInfoRequest(playerId)));
        model.addAttribute("leagueResp",leagueInfo.recordInfo(new PlayerLeagueInfoRequest(playerId, season)));
        model.addAttribute("championsResp",championsInfo.recordInfo(new PlayerChampionsInfoRequest(playerId, season)));
        model.addAttribute("totalResp",totalInfo.totalInfo(new PlayerTotalInfoRequest(playerId)));

        return "player/page";
    }


    /**
     * ?????? ??????
     */
    @NotFoundValidation
    @GetMapping("/edit/{playerId}")
    public String playerEditPage(@PathVariable Long playerId , Model model){
        List<Team> teamList  = teamRepository.findAll();
        Position[] positionList = Position.values();

        model.addAttribute("teamList",teamList);
        model.addAttribute("positionList",positionList);

        PlayerUpdateDto playerUpdateDto = new PlayerUpdateDto();
        Player player = playerRepository.findById(playerId).orElse(null);
        playerUpdateDto.settingData(player);

        model.addAttribute("playerUpdateDto",playerUpdateDto);
        return "player/edit";
    }
    @PostMapping("/edit/{playerId}")
    public String playerEdit(@PathVariable Long playerId,@ModelAttribute PlayerUpdateDto playerUpdateDto){
        playerUpdate.editPlayer(playerId,playerUpdateDto);
        return "redirect:/player/edit/" + playerId;
    }


}
