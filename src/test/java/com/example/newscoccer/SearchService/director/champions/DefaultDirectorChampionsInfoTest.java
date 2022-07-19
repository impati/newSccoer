package com.example.newscoccer.SearchService.director.champions;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO : 저장 기능 완성 시 테스트를 한번 더 해야함.
 */
@SpringBootTest
@Transactional
class DefaultDirectorChampionsInfoTest {

    @Autowired
    EntityRecordInfo<DirectorChampionsInfoRequest,DirectorChampionsInfoResponse> entityRecordInfo ;

    @Test
    @DisplayName("감독페이지의  챔피언스리그 기록 기능 테스트")
    void test(){
        DirectorChampionsInfoResponse resp = entityRecordInfo.recordInfo(new DirectorChampionsInfoRequest(1L, 0));
        System.out.println("win =  " + resp.getWin() );
        System.out.println("draw =  " + resp.getDraw() );
        System.out.println("lose =  " + resp.getLose() );
        resp.getPairRecordList().stream().forEach(ele->{
            System.out.println("name  = " + ele.getName() + " oppositeName " + ele.getOppositeName());
            System.out.println("score  = " + ele.getScore() + " oppositeName " + ele.getOppositeScore());
        });


    }

}