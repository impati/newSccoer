package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TeamLeagueRecord extends TeamRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "team_league_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private LeagueRound round;


    public static TeamLeagueRecord create(Round round, Team team){
        TeamLeagueRecord teamLeagueRecord = (TeamLeagueRecord) round.teamRecordReturn();
        teamLeagueRecord.setRound((LeagueRound) round);
        teamLeagueRecord.setTeam(team);
        teamLeagueRecord.setDirector(team.getDirector());
        return teamLeagueRecord;
    }

}
