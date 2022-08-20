package com.example.newscoccer.domain.Player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMidfielder is a Querydsl query type for Midfielder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMidfielder extends EntityPathBase<Midfielder> {

    private static final long serialVersionUID = -951111021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMidfielder midfielder = new QMidfielder("midfielder");

    public final QPlayer _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate;

    //inherited
    public final BooleanPath main;

    //inherited
    public final StringPath name;

    //inherited
    public final EnumPath<Position> position;

    //inherited
    public final NumberPath<Double> rating;

    // inherited
    public final QStat stat;

    // inherited
    public final com.example.newscoccer.domain.QTeam team;

    public QMidfielder(String variable) {
        this(Midfielder.class, forVariable(variable), INITS);
    }

    public QMidfielder(Path<? extends Midfielder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMidfielder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMidfielder(PathMetadata metadata, PathInits inits) {
        this(Midfielder.class, metadata, inits);
    }

    public QMidfielder(Class<? extends Midfielder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPlayer(type, metadata, inits);
        this.createDate = _super.createDate;
        this.id = _super.id;
        this.lastModifiedDate = _super.lastModifiedDate;
        this.main = _super.main;
        this.name = _super.name;
        this.position = _super.position;
        this.rating = _super.rating;
        this.stat = _super.stat;
        this.team = _super.team;
    }

}

