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
        System.out.println("minValue " + minValue); // 3
        System.out.println("maxValue " + maxValue); // 51
        System.out.println("avg "  + sum / n); // 22


        // when

        // then

    }
    @Test
    @DisplayName("공격수")
    public void striker() throws Exception {
        comon(Position.ST, 100);
        comon(Position.ST, 70);
        comon(Position.ST, 40);

    }

    @Test
    @DisplayName("미드 필더")
    public void 미드() throws Exception {
        comon(Position.AM, 100);
        comon(Position.AM, 70);
        comon(Position.AM, 40);

    }

    @Test
    @DisplayName("수비수")
    public void 수비수() throws Exception {
        comon(Position.CB, 100);
        comon(Position.CB, 70);
        comon(Position.CB, 40);

    }
    @Test
    @DisplayName("골기퍼")
    public void 골기퍼() throws Exception {
        comon(Position.GK, 100);
        comon(Position.GK, 70);
        comon(Position.GK, 40);

    }

    private void comon(Position position , int v) {
        // given
        int sum = 0 , n  = 1000 , minValue = 9876 , maxValue = -1;
        for(int i = 0 ;i<n;i++) {
            int ret =   DefenseDecision.defenseDecision(position,1.0,
                    v,v,v,v,
                    v,v,v,v,
                    v,v,v,v,
                    v,v
            );
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