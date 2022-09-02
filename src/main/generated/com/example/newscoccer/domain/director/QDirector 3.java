package com.example.newscoccer.domain.director;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirector is a Querydsl query type for Director
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirector extends EntityPathBase<Director> {

    private static final long serialVersionUID = -1756857003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirector director = new QDirector("director");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final com.example.newscoccer.domain.QTeam team;

    public QDirector(String variable) {
        this(Director.class, forVariable(variable), INITS);
    }

    public QDirector(Path<? extends Director> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirector(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirector(PathMetadata metadata, PathInits inits) {
        this(Director.class, metadata, inits);
    }

    public QDirector(Class<? extends Director> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new com.example.newscoccer.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

