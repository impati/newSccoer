package com.example.newscoccer.indenpent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class OtherTest {
    @Test
    @DisplayName("map Test")
    public void mapTest() throws Exception{
        // given
        Map<Integer , Integer> map = new HashMap<>();
        // when
        map.put(1,2);
        map.put(1,3);
        // then
        Assertions.assertThat(map.get(1)).isEqualTo(3);

    }
}
