package com.example.newscoccer.domain.record;

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
}

