package com.example.newscoccer.domain.record;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class PlayerChampionsRecord extends PlayerRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="player_champions_id")
    private Long id;
    private int FirstOrSecond;
}
