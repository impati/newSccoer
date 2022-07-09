package com.example.newscoccer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLeague is a Querydsl query type for League
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeague extends EntityPathBase<League> {

    private static final long serialVersionUID = -717946072L;

    public static final QLeague league = new QLeague("league");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public QLeague(String variable) {
        super(League.class, forVariable(variable));
    }

    public QLeague(Path<? extends League> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLeague(PathMetadata metadata) {
        super(League.class, metadata);
    }

}

