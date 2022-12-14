package com.example.newscoccer.controller;

import com.example.newscoccer.controller.validator.NotFoundValidation;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static java.util.stream.Collectors.toList;

/**
 * GET /league/{leagueId}  리그 페이지 레이팅순 정렬
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LeagueController {
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    @NotFoundValidation
    @GetMapping("/league/{leagueId}")
    public String leaguePage(@PathVariable Long leagueId , Model model){
        League league = leagueRepository.findById(leagueId).orElse(null);
        model.addAttribute("leagueName",league.getName());
        model.addAttribute("teams",teamRepository.findByLeague(leagueId)
                .stream().map(t->new TeamNameAndRating(t.getId(),t.getName(),t.getRating())).collect(toList()));
        return "/leaguePage";
    }

    @Data
    static class TeamNameAndRating{
        private Long teamId;
        private String name;
        private double rating;

        public TeamNameAndRating(Long teamId, String name, double rating) {
            this.teamId = teamId;
            this.name = name;
            this.rating = Math.round(rating * 100) / 100.0;
        }
    }
}
