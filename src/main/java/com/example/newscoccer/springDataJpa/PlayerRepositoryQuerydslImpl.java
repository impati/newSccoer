package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.SearchService.player.search.PlayerSearchRequest;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.QPlayer;
import com.example.newscoccer.domain.QLeague;
import com.example.newscoccer.domain.QTeam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;
import java.util.List;

import static com.example.newscoccer.domain.Player.QPlayer.player;
import static com.example.newscoccer.domain.QLeague.league;
import static com.example.newscoccer.domain.QTeam.team;
@Slf4j
@RequiredArgsConstructor
public class PlayerRepositoryQuerydslImpl implements PlayerRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Player> playerSearch(String name , Long leagueId,Long teamId , List<Position> positions) {
        BooleanBuilder builder = new BooleanBuilder();
        if(name != null)
            builder.and(player.name.contains(name));
        if(leagueId != null){
            builder.and(league.id.eq(leagueId));
        }
        if(teamId != null){
            builder.and(player.team.id.eq(teamId));
        }

        if(positions.size() != 0)
            builder.and(player.position.in(positions));

        return queryFactory
                .select(player)
                .from(player)
                .join(player.team , team)
                .join(team.league, league)
                .where(builder)
                .fetch();
    }




}
