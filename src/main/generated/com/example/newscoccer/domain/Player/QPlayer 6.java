package com.example.newscoccer.domain.Player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayer is a Querydsl query type for Player
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayer extends EntityPathBase<Player> {

    private static final long serialVersionUID = -484657259L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayer player = new QPlayer("player");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final EnumPath<Position> position = createEnum("position", Position.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final QStat stat;

    public final com.example.newscoccer.domain.QTeam team;

    public QPlayer(String variable) {
        this(Player.class, forVariable(variable), INITS);
    }

    public QPlayer(Path<? extends Player> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayer(PathMetadata metadata, PathInits inits) {
        this(Player.class, metadata, inits);
    }

    public QPlayer(Class<? extends Player> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stat = inits.isInitialized("stat") ? new QStat(forProperty("stat")) : null;
        this.team = inits.isInitialized("team") ? new com.example.newscoccer.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

