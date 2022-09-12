package com.example.newscoccer.indenpent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class subListTest {

    @Test
    @DisplayName("")
    public void subListTest() throws Exception{
        // given

        List<Integer> integers = new ArrayList<>();

        for(int i =  0;i<22;i++)integers.add(i);
        // when

        // 0 , 1 , 2, 3, 4,5,6,7,8,9
        List<Integer> listA = integers.subList(0,11);
        List<Integer> listB = integers.subList(11,22);

        // then


        Assertions.assertThat(listA.size()).isEqualTo(11);
        Assertions.assertThat(listB.size()).isEqualTo(11);
    }
}
