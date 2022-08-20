package com.example.newscoccer.auto.Game;

import lombok.Data;

@Data
public class GradeDto {

    private Double pass;
    private Double shooting;
    private Double validShooting;

    private Double foul;
    private Double defense;

    public GradeDto(Double pass, Double shooting, Double validShooting, Double foul, Double defense) {
        this.pass = orGet(pass,28.3);
        this.shooting = orGet(shooting,1.4);
        this.validShooting = orGet(validShooting,1.05);
        this.foul = orGet(foul,1.9);
        this.defense = orGet(defense,16.8);
    }
    private Double orGet(Double doubleValue , double value){
        if(doubleValue == null) return value;
        else return doubleValue;
    }
}
