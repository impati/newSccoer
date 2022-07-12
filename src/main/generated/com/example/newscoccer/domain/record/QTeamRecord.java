package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamRecord is a Querydsl query type for TeamRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamRecord extends EntityPathBase<TeamRecord> {

    private static final long serialVersionUID = 1429466226L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamRecord teamRecord = new QTeamRecord("teamRecord");

    public final NumberPath<Integer> cornerKick = createNumber("cornerKick", Integer.class);

    public final NumberPath<Integer> foul = createNumber("foul", Integer.class);

    public final NumberPath<Integer> freeKick = createNumber("freeKick", Integer.class);

    public final NumberPath<Integer> GoodDefense = createNumber("GoodDefense", Integer.class);

    public final NumberPath<Double> grade = createNumber("grade", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MatchResult> matchResult = createEnum("matchResult", MatchResult.class);

    public final NumberPath<Integer> oppositeScore = createNumber("oppositeScore", Integer.class);

    public final NumberPath<Integer> pass = createNumber("pass", Integer.class);

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> share = createNumber("share", Integer.class);

    public final NumberPath<Integer> shooting = createNumber("shooting", Integer.class);

    public final com.example.newscoccer.domain.QTeam team;

    public final NumberPath<Integer> validShooting = createNumber("validShooting", Integer.class);

    public QTeamRecord(String variable) {
        this(TeamRecord.class, forVariable(variable), INITS);
    }

    public QTeamRecord(Path<? extends TeamRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamRecord(PathMetadata metadata, PathInits inits) {
        this(TeamRecord.class, metadata, inits);
    }

    public QTeamRecord(Class<? extends TeamRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new com.example.newscoccer.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

