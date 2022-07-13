package com.example.newscoccer.springDataJpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class RoundRepositoryTest {
    @Autowired
    RoundRepository roundRepository;
    @Test
    @DisplayName("시즌으로 라운드 개수 조회")
    void findBySeason(){
         Assertions.assertThat(roundRepository.findBySeason(0)).isGreaterThan(0);
    }


}