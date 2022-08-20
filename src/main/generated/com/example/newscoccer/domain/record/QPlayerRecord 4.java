package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerRecord is a Querydsl query type for PlayerRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerRecord extends EntityPathBase<PlayerRecord> {

    private static final long serialVersionUID = 107710710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerRecord playerRecord = new QPlayerRecord("playerRecord");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    public final NumberPath<Integer> assist = createNumber("assist", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> foul = createNumber("foul", Integer.class);

    public final NumberPath<Integer> goal = createNumber("goal", Integer.class);

    public final NumberPath<Integer> goodDefense = createNumber("goodDefense", Integer.class);

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBest = createBoolean("isBest");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<MatchResult> matchResult = createEnum("matchResult", MatchResult.class);

    public final NumberPath<Integer> pass = createNumber("pass", Integer.class);

    public final com.example.newscoccer.domain.Player.QPlayer player;

    public final EnumPath<com.example.newscoccer.domain.Player.Position> position = createEnum("position", com.example.newscoccer.domain.Player.Position.class);

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final com.example.newscoccer.domain.Round.QRound round;

    public final NumberPath<Integer> shooting = createNumber("shooting", Integer.class);

    public final com.example.newscoccer.domain.QTeam team;

    public final NumberPath<Integer> validShooting = createNumber("validShooting", Integer.class);

    public QPlayerRecord(String variable) {
        this(PlayerRecord.class, forVariable(variable), INITS);
    }

    public QPlayerRecord(Path<? extends PlayerRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerRecord(PathMetadata metadata, PathInits inits) {
        this(PlayerRecord.class, metadata, inits);
    }

    public QPlayerRecord(Class<? extends PlayerRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.player = inits.isInitialized("player") ? new com.example.newscoccer.domain.Player.QPlayer(forProperty("player"), inits.get("player")) : null;
        this.round = inits.isInitialized("round") ? new com.example.newscoccer.domain.Round.QRound(forProperty("round")) : null;
        this.team = inits.isInitialized("team") ? new com.example.newscoccer.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

