package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public class DirectorRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_id")
    private Round round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MatchResult mathResult;

    private int rank;

    public static DirectorRecord create(Round round , Director director){
        DirectorRecord directorRecord = new DirectorRecord();
        directorRecord.setRound(round);
        directorRecord.setTeam(director.getTeam());
        directorRecord.setDirector(director);
        return directorRecord;
    }
    public void update(MatchResult mathResult){
        this.mathResult = mathResult;
    }

}
