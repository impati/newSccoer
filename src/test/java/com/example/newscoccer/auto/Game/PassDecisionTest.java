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

    @Test
    @DisplayName("?????????  ?????????")
    public void striker() throws Exception{
        // given

        common(Position.ST,100,100,100,100,100,100,100,100,100);
        common(Position.ST,70,70,70,70,70,70,70,70,70);
        common(Position.ST,40,40,40,40,40,40,40,40,40);


    }
    @Test
    @DisplayName("????????????  ?????????")
    public void mid() throws Exception{
        // given

        common(Position.AM,100,100,100,100,100,100,100,100,100);
        common(Position.AM,70,70,70,70,70,70,70,70,70);
        common(Position.AM,40,40,40,40,40,40,40,40,40);


    }
   @Test
    @DisplayName("defense  ?????????")
    public void defense() throws Exception{
        // given

        common(Position.CB,100,100,100,100,100,100,100,100,100);
        common(Position.CB,70,70,70,70,70,70,70,70,70);
        common(Position.CB,40,40,40,40,40,40,40,40,40);


    }

    @Test
    @DisplayName("keeper  ?????????")
    public void keeper() throws Exception{
        // given

        common(Position.GK,100,100,100,100,100,100,100,100,100);
        common(Position.GK,70,70,70,70,70,70,70,70,70);
        common(Position.GK,40,40,40,40,40,40,40,40,40);


    }




    private void common(Position position , int a,int b,int c,int d,int e,int f,int g,int h,int k) {
        //
        PassDecision p = new PassDecision();
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret = p.passDecision(position, 1.0,a,b,c,d,e,f,g,h,k);
            sum += ret;
            minValue = Math.min(minValue,ret);
            maxValue = Math.max(maxValue,ret);
        }
        // then
        System.out.println(" minValue " + minValue);
        System.out.println(" maxValue " + maxValue);
        System.out.println(" avg "  + sum / n);
    }


}
