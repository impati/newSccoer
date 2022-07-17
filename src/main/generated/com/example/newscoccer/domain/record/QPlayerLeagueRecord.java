package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerLeagueRecord is a Querydsl query type for PlayerLeagueRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerLeagueRecord extends EntityPathBase<PlayerLeagueRecord> {

    private static final long serialVersionUID = -1271441339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerLeagueRecord playerLeagueRecord = new QPlayerLeagueRecord("playerLeagueRecord");

    public final QPlayerRecord _super;

    //inherited
    public final NumberPath<Integer> assist;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    //inherited
    public final NumberPath<Integer> foul;

    //inherited
    public final NumberPath<Integer> goal;

    //inherited
    public final NumberPath<Integer> goodDefense;

    //inherited
    public final NumberPath<Integer> grade;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isBest;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate;

    //inherited
    public final EnumPath<MatchResult> matchResult;

    //inherited
    public final NumberPath<Integer> pass;

    // inherited
    public final com.example.newscoccer.domain.Player.QPlayer player;

    //inherited
    public final EnumPath<com.example.newscoccer.domain.Player.Position> position;

    //inherited
    public final NumberPath<Integer> rank;

    //inherited
    public final NumberPath<Double> rating;

    // inherited
    public final com.example.newscoccer.domain.Round.QRound round;

    //inherited
    public final NumberPath<Integer> shooting;

    // inherited
    public final com.example.newscoccer.domain.QTeam team;

    //inherited
    public final NumberPath<Integer> validShooting;

    public QPlayerLeagueRecord(String variable) {
        this(PlayerLeagueRecord.class, forVariable(variable), INITS);
    }

    public QPlayerLeagueRecord(Path<? extends PlayerLeagueRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerLeagueRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerLeagueRecord(PathMetadata metadata, PathInits inits) {
        this(PlayerLeagueRecord.class, metadata, inits);
    }

    public QPlayerLeagueRecord(Class<? extends PlayerLeagueRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPlayerRecord(type, metadata, inits);
        this.assist = _super.assist;
        this.createDate = _super.createDate;
        this.foul = _super.foul;
        this.goal = _super.goal;
        this.goodDefense = _super.goodDefense;
        this.grade = _super.grade;
        this.isBest = _super.isBest;
        this.lastModifiedDate = _super.lastModifiedDate;
        this.matchResult = _super.matchResult;
        this.pass = _super.pass;
        this.player = _super.player;
        this.position = _super.position;
        this.rank = _super.rank;
        this.rating = _super.rating;
        this.round = _super.round;
        this.shooting = _super.shooting;
        this.team = _super.team;
        this.validShooting = _super.validShooting;
    }

}

