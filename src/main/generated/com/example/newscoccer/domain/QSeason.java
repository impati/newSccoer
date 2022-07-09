package com.example.newscoccer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeason is a Querydsl query type for Season
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeason extends EntityPathBase<Season> {

    private static final long serialVersionUID = -517530660L;

    public static final QSeason season = new QSeason("season");

    public final NumberPath<Integer> CurrentChampionsRoundSt = createNumber("CurrentChampionsRoundSt", Integer.class);

    public final NumberPath<Integer> CurrentLeagueRoundSt = createNumber("CurrentLeagueRoundSt", Integer.class);

    public final NumberPath<Integer> currentSeason = createNumber("currentSeason", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> LastLeagueRoundSt = createNumber("LastLeagueRoundSt", Integer.class);

    public QSeason(String variable) {
        super(Season.class, forVariable(variable));
    }

    public QSeason(Path<? extends Season> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeason(PathMetadata metadata) {
        super(Season.class, metadata);
    }

}

