package com.example.newscoccer.domain.Round;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLeagueRound is a Querydsl query type for LeagueRound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeagueRound extends EntityPathBase<LeagueRound> {

    private static final long serialVersionUID = 1695664518L;

    public static final QLeagueRound leagueRound = new QLeagueRound("leagueRound");

    public final QRound _super = new QRound(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    //inherited
    public final NumberPath<Integer> roundSt = _super.roundSt;

    //inherited
    public final EnumPath<RoundStatus> roundStatus = _super.roundStatus;

    //inherited
    public final NumberPath<Integer> season = _super.season;

    public QLeagueRound(String variable) {
        super(LeagueRound.class, forVariable(variable));
    }

    public QLeagueRound(Path<? extends LeagueRound> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLeagueRound(PathMetadata metadata) {
        super(LeagueRound.class, metadata);
    }

}

