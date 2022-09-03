package com.example.newscoccer.domain.Round;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLeagueRound is a Querydsl query type for LeagueRound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeagueRound extends EntityPathBase<LeagueRound> {

    private static final long serialVersionUID = 1695664518L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLeagueRound leagueRound = new QLeagueRound("leagueRound");

    public final QRound _super = new QRound(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.example.newscoccer.domain.QLeague league;

    //inherited
    public final NumberPath<Integer> roundSt = _super.roundSt;

    //inherited
    public final EnumPath<RoundStatus> roundStatus = _super.roundStatus;

    //inherited
    public final NumberPath<Integer> season = _super.season;

    public QLeagueRound(String variable) {
        this(LeagueRound.class, forVariable(variable), INITS);
    }

    public QLeagueRound(Path<? extends LeagueRound> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLeagueRound(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLeagueRound(PathMetadata metadata, PathInits inits) {
        this(LeagueRound.class, metadata, inits);
    }

    public QLeagueRound(Class<? extends LeagueRound> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.league = inits.isInitialized("league") ? new com.example.newscoccer.domain.QLeague(forProperty("league")) : null;
    }

}

