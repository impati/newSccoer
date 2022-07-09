package com.example.newscoccer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 *
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class League extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="league_id")
    private Long id;
    private String name;
    private int rating;

    public League(String name) {
        this.name = name;
        this.rating = 1500;
    }
}
