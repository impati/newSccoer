package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.SearchService.round.common.faceToHead.TopPlayerDto;
import com.example.newscoccer.domain.Player.QPlayer;
import com.example.newscoccer.domain.QTeam;
import com.example.newscoccer.domain.Round.QRound;
import com.example.newscoccer.domain.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.newscoccer.domain.record.QPlayerChampionsRecord.playerChampionsRecord;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlayerChampionsRepositoryQuerydslImpl implements PlayerChampionsRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    /**
     * 미사용
     * @param team
     * @param season
     * @param roundSt
     * @param firstAndSecond
     * @return
     */
    private List<TopPlayerDto> findTop5Player( Team team , Integer  season , Integer roundSt,Integer firstAndSecond){
        CaseBuilder caseBuilder = new CaseBuilder();

        return queryFactory.select(Projections.constructor(TopPlayerDto.class, QPlayer.player.name,playerChampionsRecord.goal.sum(),playerChampionsRecord.assist.sum(),QPlayer.player.rating))
                .join(playerChampionsRecord.player , QPlayer.player)
                .join(playerChampionsRecord.round , QRound.round)
                .join(playerChampionsRecord.team , QTeam.team)
                .where(QTeam.team.eq(team),
                        QRound.round.season.eq(season),
                        QRound.round.roundSt.goe(roundSt)
                        )
                .groupBy(QPlayer.player.id)
                .orderBy(playerChampionsRecord.goal.sum().add(playerChampionsRecord.assist.sum()).desc(),
                        QPlayer.player.rating.desc())
                .offset(0)
                .limit(5)
                .fetch();
    }



}
