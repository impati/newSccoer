package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.TeamChampionsRecord;
import com.example.newscoccer.springDataJpa.dto.TeamScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.Size;
import java.util.List;

public interface TeamChampionsRecordRepository extends JpaRepository<TeamChampionsRecord,Long> {


    List<TeamChampionsRecord> findByRound(Round round);

    /**
     * 시즌 , 챔피언스 라운드에 진출한 팀을 가져옴.
     */
    @Query("select t from TeamChampionsRecord  tcr " +
            " join tcr.team t " +
            " join tcr.round r " +
            "where r.season = :season and r.roundSt = :roundSt")
    List<Team> findTeam(@Param("season") int season , @Param("roundSt") int roundSt);

    /**
     * 시즌 , 라운드 , 인덱스 정보 로 챔피언스 결과를 가져옴.
     * result 결과가  최대 4 개.
     */
    @Query("select tcr from TeamChampionsRecord tcr " +
            " join fetch tcr.round r " +
            " where r.season =:season and r.roundSt =:roundSt and r.index =:index")
    List<TeamChampionsRecord> findByRoundStResults (@Param("season") int season , @Param("roundSt") int roundSt , @Param("index") int index);


    /**
     * 시즌,라운드 인덱스로 그 라운드에서 팀과 팀의 총 스코어를 가져옴.
     */
    @Query(" select new com.example.newscoccer.springDataJpa.dto.TeamScoreDto(tcr.team,sum(tcr.score)) " +
            " from TeamChampionsRecord  tcr " +
            " join  tcr.round r " +
            " where r.season =:season and r.roundSt =:roundSt and r.index =:index " +
            " group by tcr.team ")
    List<TeamScoreDto> findTeamScore(@Param("season") int season , @Param("roundSt") int roundSt ,@Param("index") int index);



}
