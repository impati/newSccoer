package com.example.newscoccer.testSupport;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class testControllerTest {


    @Autowired
    LeagueRepository leagueRepository;

    @Test
    @DisplayName("테스트 컨트롤러 테스트")
    public void test() throws Exception{
        // given
        TestController controller = new TestController();
        League league = controller.createLeague();
        // when
        // then

        Assertions.assertThat(controller.getLeagueRepository()).isNotNull();
        Assertions.assertThat(league.getId()).isNotNull();
        Assertions.assertThat(league.getName()).isEqualTo("testLeague");

    }
}
