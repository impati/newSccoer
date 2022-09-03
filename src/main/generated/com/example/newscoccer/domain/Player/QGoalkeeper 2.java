package com.example.newscoccer.domain.Player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoalkeeper is a Querydsl query type for Goalkeeper
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoalkeeper extends EntityPathBase<Goalkeeper> {

    private static final long serialVersionUID = -1873169799L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoalkeeper goalkeeper = new QGoalkeeper("goalkeeper");

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

    public QGoalkeeper(String variable) {
        this(Goalkeeper.class, forVariable(variable), INITS);
    }

    public QGoalkeeper(Path<? extends Goalkeeper> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoalkeeper(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoalkeeper(PathMetadata metadata, PathInits inits) {
        this(Goalkeeper.class, metadata, inits);
    }

    public QGoalkeeper(Class<? extends Goalkeeper> type, PathMetadata metadata, PathInits inits) {
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

