package com.example.newscoccer.SearchService.common;

import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.TeamChampionsRecordRepository;
import com.example.newscoccer.springDataJpa.TeamLeagueRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  TODO : 기록이 조금 더 생긴다면 테스트
 */
@SpringBootTest
class RankInfoTest {

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;

    @Autowired
    TeamChampionsRecordRepository teamChampionsRecordRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Test
    @DisplayName("rank 기능 테스트")
    void test(){

        Director director = directorRepository.findById(1L).orElse(null);
        RankInfo rankInfo = new RankInfo();
        rankInfo.calcRank(teamChampionsRecordRepository.findByDirector(director), TeamChampionsRecord.class);



    }

}