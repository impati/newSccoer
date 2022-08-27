package com.example.newscoccer.SearchService.director.search;


import com.example.newscoccer.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorSearchResponse {
    private Long directorId;
    private String directorName;
    private String teamName;

    public DirectorSearchResponse(Long directorId, String directorName, Team team) {
        this.directorId = directorId;
        this.directorName = directorName;
        if(team != null)
            this.teamName = team.getName();
    }
}
