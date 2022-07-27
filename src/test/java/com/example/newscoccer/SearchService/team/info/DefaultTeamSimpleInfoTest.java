package com.example.newscoccer.SearchService.team.info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DefaultTeamSimpleInfoTest {

    @Autowired
    EntitySimpleInfo<TeamSimpleInfoRequest , TeamSimpleInfoResponse> recordInfo;
    @Autowired
    TeamRepository teamRepository;
    @Test
    @DisplayName("팀 심플 정보")
    public void teamSimpleInfo() throws Exception{
        // given
        Team team = teamRepository.findByName("아우크스부르크").orElse(null);
        // when
        TeamSimpleInfoResponse resp = recordInfo.simpleInfo(new TeamSimpleInfoRequest(team.getId()));
        Assertions.assertThat(resp.getTeamName()).isEqualTo("아우크스부르크");
        Assertions.assertThat(resp.getLeagueName()).isEqualTo("분데스리가");
        Assertions.assertThat(resp.getRating()).isEqualTo(team.getRating());

        // then

    }
}