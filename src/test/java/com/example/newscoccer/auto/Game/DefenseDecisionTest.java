package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefenseDecisionTest {


    @Test
    @DisplayName("high test")
    public void high() throws Exception{
        // given
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret =   DefenseDecision.defenseDecision(Position.ST,1.0,
                    100,100,100,100,
                    100,100,100,100,
                    100,100,100,100,
                    100,100
            );
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println("minValue " + minValue);
        System.out.println("maxValue " + maxValue);
        System.out.println("avg "  + sum / n);


        // when

        // then

    }
    @Test
    @DisplayName("normal test")
    public void normal() throws Exception{
        // given
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret =   DefenseDecision.defenseDecision(Position.ST,1.0,
                    70,70,70,70,
                    70,70,70,70,
                    70,70,70,70,
                    70,70
            );
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println("minValue " + minValue);
        System.out.println("maxValue " + maxValue);
        System.out.println("avg "  + sum / n);


        // when

        // then

    }
}