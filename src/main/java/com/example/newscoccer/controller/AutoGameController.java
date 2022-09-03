package com.example.newscoccer.controller;

import com.example.newscoccer.auto.Game.AutoGameRegister;
import com.example.newscoccer.auto.lineUp.AutoLineUp;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 리그 저장 기능만을 지원
 */
@Slf4j
@Controller
@RequestMapping("/auto-game")
@RequiredArgsConstructor
public class AutoGameController {
    private final AutoLineUp autoLineUp;
    private final AutoGameRegister autoGameRegister;
    private final RoundRepository roundRepository;
    @GetMapping
    public String autoGame(){
        int season = SeasonUtils.currentSeason;
        int roundSt = SeasonUtils.currentLeagueRoundSt;
        roundRepository.findLeagueRound(season,roundSt)
                .stream()
                .forEach(ele->{
                    autoLineUp.autoLineUp(ele.getId());
                    autoGameRegister.autoGameRegister(ele.getId());
                });
        return "redirect:/round/league";
    }
    @GetMapping("/{count}")
    public void autoGameCount(@PathVariable Integer count){
        while(count != 0) {
            int season = SeasonUtils.currentSeason;
            int roundSt = SeasonUtils.currentLeagueRoundSt;
            roundRepository.findLeagueRound(season, roundSt)
                    .stream()
                    .forEach(ele -> {
                        autoLineUp.autoLineUp(ele.getId());
                        autoGameRegister.autoGameRegister(ele.getId());
                    });
            count -=1;
        }
    }
}
