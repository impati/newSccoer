package com.example.newscoccer.SearchService.round.common.faceToHead;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopPlayerDto {
    private String  playerName;
    private Long goal;
    private Long assist;
    private double rating;
    public void update(int goal,int assist){
        this.goal += goal;
        this.assist += assist;
    }
}
