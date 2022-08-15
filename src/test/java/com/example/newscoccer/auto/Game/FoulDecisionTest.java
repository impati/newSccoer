package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FoulDecisionTest {


    @Test
    @DisplayName("high foul")
    public void highFoul() throws Exception{


        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0;i<n;i++){
            int ret = FoulDecision.foulDecision(Position.ST,1.0,100,100,100,100,100);
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println("minValue " + minValue);
        System.out.println("maxValue " + maxValue);
        System.out.println("avg "  + sum / n);

    }
    @Test
    @DisplayName("normal foul")
    public void normal() throws Exception{


        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0;i<n;i++){
            int ret = FoulDecision.foulDecision(Position.ST,1.0,70,70,70,70,70);
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println("minValue " + minValue);
        System.out.println("maxValue " + maxValue);
        System.out.println("avg "  + sum / n);

    }

    @Test
    @DisplayName("lower foul")
    public void lower() throws Exception{


        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0;i<n;i++){
            int ret = FoulDecision.foulDecision(Position.ST,1.0,40,40,40,40,40);
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println("minValue " + minValue);
        System.out.println("maxValue " + maxValue);
        System.out.println("avg "  + sum / n);

    }
}