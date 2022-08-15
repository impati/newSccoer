package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoGameDtoTest {


    @Test
    @DisplayName("normal freeKick Test")
    public void normalFreeKick() throws Exception{
        // given
        int t = 1000;
        int ans = 0;
        while(t !=0 ) {
            int totalFoul = 0;
            for (int i = 0; i < 10; i++) {
                totalFoul += FoulDecision.foulDecision(Position.ST, 1.0, 70, 70, 70, 70, 70);
            }
            // when
            totalFoul /= 10;
            int value = 50;

            while (totalFoul != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 100);
                int n = RandomNumber.returnRandomNumber(0, value);
                if (rn * 2  < n) {
                    ans += 1;
                }
                totalFoul -= 1;
            }
            t -=1;
        }
        // then
        System.out.println("평균 골수" + ( (double) ans / (double) 1000));
    }
    @Test
    @DisplayName("high freeKick Test")
    public void highFreeKick() throws Exception{
        // given
        int t = 1000;
        int ans = 0;
        while(t !=0 ) {
            int totalFoul = 0;
            for (int i = 0; i < 10; i++) {
                totalFoul += FoulDecision.foulDecision(Position.ST, 1.0, 70, 70, 70, 70, 70);
            }
            // when
            totalFoul /= 10;
            int value = 100;

            while (totalFoul != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 100);
                int n = RandomNumber.returnRandomNumber(0, value);
                if (rn * 2  < n) {
                    ans += 1;
                }
                totalFoul -= 1;
            }
            t -=1;
        }
        // then
        System.out.println("평균 골수" + ( (double) ans / (double) 1000));
    }
}