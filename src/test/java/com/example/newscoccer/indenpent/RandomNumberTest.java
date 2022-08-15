package com.example.newscoccer.indenpent;


import com.example.newscoccer.support.RandomNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomNumberTest {

    @Test
    @DisplayName("RandomNumberTest")
    public void RandomNumberTest() throws Exception{

        for(int i = 0 ; i < 1000 ; i++){
            int s = RandomNumber.returnRandomNumber(0,100);
            int e = RandomNumber.returnRandomNumber(s,100);
            int r = RandomNumber.returnRandomNumber(s,e);
            Assertions.assertThat(r >= s ).isTrue();
            Assertions.assertThat(r <= e ).isTrue();


        }
    }
}
