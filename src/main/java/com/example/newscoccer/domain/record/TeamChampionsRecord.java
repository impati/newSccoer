package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.Round.ChampionsRound;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TeamChampionsRecord extends TeamRecord{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "team_champions_id")
    private Long id;
    private int FirstOrSecond;
    @ManyToOne
    @JoinColumn(name = "round_id")
    private ChampionsRound round;

    public static TeamChampionsRecord create(Round round , Team team,int firstOrSecond){
        TeamChampionsRecord teamChampionsRecord = (TeamChampionsRecord) round.teamRecordReturn();
        teamChampionsRecord.setRound((ChampionsRound) round);
        teamChampionsRecord.setTeam(team);
        teamChampionsRecord.setFirstOrSecond(firstOrSecond);
        teamChampionsRecord.setDirector(team.getDirector());
        return teamChampionsRecord;
    }


}

