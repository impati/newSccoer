package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PassDecisionTest {

    @Test
    @DisplayName("max pass Test")
    public void passTest() throws Exception{
        // given

        PassDecision p = new PassDecision();
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret = p.passDecision(Position.ST, 1.0,100, 100, 100, 100, 100, 100, 100, 100, 100);
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
    @DisplayName("normal pass Test")
    public void normalPassTest() throws Exception{
        // given

        PassDecision p = new PassDecision();
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret = p.passDecision(Position.ST, 1.0,70, 70, 70, 70, 70, 70, 70, 70, 70);
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
    @DisplayName("lower pass Test")
    public void lowerPassTest() throws Exception{
        // given

        PassDecision p = new PassDecision();
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret = p.passDecision(Position.ST, 1.0,40, 40, 40, 40, 40, 40, 40, 40, 40);
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