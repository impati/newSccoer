package com.example.newscoccer.domain.record;

import lombok.Getter;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;

@Getter
@Entity
public class DirectorLeagueRecord extends DirectorRecord{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="director_league_id")
    private Long id;
}
