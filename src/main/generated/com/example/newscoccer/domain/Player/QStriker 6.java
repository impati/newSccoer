package com.example.newscoccer.domain.Player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStriker is a Querydsl query type for Striker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStriker extends EntityPathBase<Striker> {

    private static final long serialVersionUID = 767299788L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStriker striker = new QStriker("striker");

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

    public QStriker(String variable) {
        this(Striker.class, forVariable(variable), INITS);
    }

    public QStriker(Path<? extends Striker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStriker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStriker(PathMetadata metadata, PathInits inits) {
        this(Striker.class, metadata, inits);
    }

    public QStriker(Class<? extends Striker> type, PathMetadata metadata, PathInits inits) {
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

