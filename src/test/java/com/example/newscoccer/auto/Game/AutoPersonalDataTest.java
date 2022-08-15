package com.example.newscoccer.auto.Game;

import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoPersonalDataTest {



    @Test
    @DisplayName("normalShooting")
    public void normalShooting() throws Exception{



        int pass = 6;
        int value = 1000;
        int shooting = 0 , validShooting = 0 , goal = 0;


        int t = 1000;
        while(t != 0) {
            pass = 6;
            while (pass != 0) {
                int rn = RandomNumber.returnRandomNumber(0, 1500);
                int n = RandomNumber.returnRandomNumber(0, value);
                if(rn * 2  < n ) shooting += 1;
                if (rn * 4 < n) validShooting += 1;
                if (rn * 6 < n) goal += 1;
                pass -= 1;
            }
            t -=1;
        }

        System.out.println("shooting avg =" + shooting / (double)1000);
        System.out.println("shooting avg =" + validShooting / (double)1000);
        System.out.println("shooting avg =" + goal / (double)1000);

    }

}