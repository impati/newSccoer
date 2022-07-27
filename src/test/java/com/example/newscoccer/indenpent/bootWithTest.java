package com.example.newscoccer.indenpent;

import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class bootWithTest {

    @Autowired
    TeamRepository teamRepository;
    @Test
    @DisplayName("findByName 테스트")
    public void String() throws Exception{
        // given
        Optional<Team> team1 = teamRepository.findByName("아우크스부르크");
        Optional<Team> team2 = teamRepository.findByName("아우크스");
        // when
        // then
        Assertions.assertThat(team1.isPresent()).isTrue();
        Assertions.assertThat(team2.isPresent()).isFalse();


    }

}
