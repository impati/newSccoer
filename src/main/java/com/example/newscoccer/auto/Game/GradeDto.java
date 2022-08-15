package com.example.newscoccer.auto.Game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeDto {

    private Double pass;
    private Double shooting;
    private Double validShooting;

    private Double foul;
    private Double defense;
}
