package com.example.newscoccer.domain.record;

import lombok.Cleanup;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DirectorChampionsRecord extends DirectorRecord{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_champions_id")
    private Long id;
    private int FirstOrSecond;
}
