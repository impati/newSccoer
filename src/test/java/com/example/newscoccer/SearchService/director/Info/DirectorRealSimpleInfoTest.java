package com.example.newscoccer.SearchService.director.Info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultDirectorSimpleInfoTest {

    @Autowired
    EntitySimpleInfo<DirectorSimpleInfoRequest , DirectorSimpleInfoResponse> entitySimpleInfo;

    @Test
    @DisplayName("감독 기본 정보 기능")
    void simpleInfo(){

        DirectorSimpleInfoResponse response1 = entitySimpleInfo.simpleInfo(new DirectorSimpleInfoRequest(1L));


        Assertions.assertThat(response1.getTeamName()).isEqualTo("바이에른 뮌헨");
        Assertions.assertThat(response1.getName()).isEqualTo("텔레 산타나");
    }




}