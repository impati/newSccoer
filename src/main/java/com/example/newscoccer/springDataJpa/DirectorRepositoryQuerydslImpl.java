package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.QTeam;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


import static com.example.newscoccer.domain.director.QDirector.director;

@RequiredArgsConstructor
public class DirectorRepositoryQuerydslImpl implements DirectorRepositoryQuerydsl{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Director> findDirectorList(String name , League league , Team team) {

        return jpaQueryFactory.selectFrom(director)
                .leftJoin(director.team, QTeam.team)
                .where(nameSearch(name),teamSearch(team),leagueSearch(league))
                .fetch();

    }

    private BooleanExpression nameSearch(String name) {
        if(name != null)
            return director.name.contains(name);
        return null;
    }

    private BooleanExpression teamSearch(Team team) {
        if(team != null)
            return director.team.eq(team);
        return null;
    }
    private BooleanExpression leagueSearch(League league) {
        if(league != null)
            return QTeam.team.league.eq(league);
        return null;
    }
}
