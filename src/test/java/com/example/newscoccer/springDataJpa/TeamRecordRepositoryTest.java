package com.example.newscoccer.springDataJpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class TeamRecordRepositoryTest {
    @Autowired
    List<TeamRecordRepository> teamRecordRepositoryList = new ArrayList<>();
    @Test
    @DisplayName("TeamRecordRepository 적용 여부")
    public void setTeamRecordRepository() throws Exception{
        Assertions.assertThat(teamRecordRepositoryList.size()).isEqualTo(2);
    }

}