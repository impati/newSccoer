package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamLeagueRecord is a Querydsl query type for TeamLeagueRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamLeagueRecord extends EntityPathBase<TeamLeagueRecord> {

    private static final long serialVersionUID = 1970155201L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamLeagueRecord teamLeagueRecord = new QTeamLeagueRecord("teamLeagueRecord");

    public final QTeamRecord _super;

    //inherited
    public final NumberPath<Integer> cornerKick;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    // inherited
    public final com.example.newscoccer.domain.director.QDirector director;

    //inherited
    public final NumberPath<Integer> foul;

    //inherited
    public final NumberPath<Integer> freeKick;

    //inherited
    public final NumberPath<Integer> GoodDefense;

    //inherited
    public final NumberPath<Double> grade;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate;

    //inherited
    public final EnumPath<MatchResult> matchResult;

    //inherited
    public final NumberPath<Integer> oppositeScore;

    //inherited
    public final NumberPath<Integer> pass;

    //inherited
    public final NumberPath<Integer> rank;

    //inherited
    public final NumberPath<Double> rating;

    public final com.example.newscoccer.domain.Round.QLeagueRound round;

    //inherited
    public final NumberPath<Integer> score;

    //inherited
    public final NumberPath<Integer> share;

    //inherited
    public final NumberPath<Integer> shooting;

    // inherited
    public final com.example.newscoccer.domain.QTeam team;

    //inherited
    public final NumberPath<Integer> validShooting;

    public QTeamLeagueRecord(String variable) {
        this(TeamLeagueRecord.class, forVariable(variable), INITS);
    }

    public QTeamLeagueRecord(Path<? extends TeamLeagueRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamLeagueRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamLeagueRecord(PathMetadata metadata, PathInits inits) {
        this(TeamLeagueRecord.class, metadata, inits);
    }

    public QTeamLeagueRecord(Class<? extends TeamLeagueRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QTeamRecord(type, metadata, inits);
        this.cornerKick = _super.cornerKick;
        this.createDate = _super.createDate;
        this.director = _super.director;
        this.foul = _super.foul;
        this.freeKick = _super.freeKick;
        this.GoodDefense = _super.GoodDefense;
        this.grade = _super.grade;
        this.lastModifiedDate = _super.lastModifiedDate;
        this.matchResult = _super.matchResult;
        this.oppositeScore = _super.oppositeScore;
        this.pass = _super.pass;
        this.rank = _super.rank;
        this.rating = _super.rating;
        this.round = inits.isInitialized("round") ? new com.example.newscoccer.domain.Round.QLeagueRound(forProperty("round"), inits.get("round")) : null;
        this.score = _super.score;
        this.share = _super.share;
        this.shooting = _super.shooting;
        this.team = _super.team;
        this.validShooting = _super.validShooting;
    }

}

