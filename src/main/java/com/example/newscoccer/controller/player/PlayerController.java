package com.example.newscoccer.controller.player;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *  Get  /player/register -> 선수 등록 페이지
 *  Post /player/register -> 선수 등록
 *
 *  Get /player/players ->선수 전체 목록
 *
 *  Get /player/{id}?season= -> 선수 페이지
 *
 *  Get /player/edit/{id} ->선수 수정 페이지
 *  Post /player/edit/{id} -> 선수 수정
 *
 */
@Slf4j
@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final TeamRepository teamRepository;

    @GetMapping("/register")
    public String playerRegisterPage(Model model){
        List<Team> teamList  = teamRepository.findAll();
        Position[] positionList = Position.values();
        model.addAttribute("teamList",teamList);
        model.addAttribute("positionList",positionList);
        model.addAttribute("newStat",new Stat());
        return "";
    }
    @PostMapping("/register")
    public String playerRegister(@ModelAttribute PlayerRegisterForm playerRegisterForm){
        return "";
    }




}
