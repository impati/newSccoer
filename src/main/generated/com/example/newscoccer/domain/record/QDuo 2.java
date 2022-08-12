package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDuo is a Querydsl query type for Duo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDuo extends EntityPathBase<Duo> {

    private static final long serialVersionUID = 334725914L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDuo duo = new QDuo("duo");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    public final NumberPath<Long> assistPlayerId = createNumber("assistPlayerId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> goalPlayerId = createNumber("goalPlayerId", Long.class);

    public final EnumPath<GoalType> goalType = createEnum("goalType", GoalType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.example.newscoccer.domain.Round.QRound round;

    public QDuo(String variable) {
        this(Duo.class, forVariable(variable), INITS);
    }

    public QDuo(Path<? extends Duo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDuo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDuo(PathMetadata metadata, PathInits inits) {
        this(Duo.class, metadata, inits);
    }

    public QDuo(Class<? extends Duo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.round = inits.isInitialized("round") ? new com.example.newscoccer.domain.Round.QRound(forProperty("round")) : null;
    }

}

