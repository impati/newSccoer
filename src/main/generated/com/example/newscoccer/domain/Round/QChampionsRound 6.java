package com.example.newscoccer.domain.Round;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChampionsRound is a Querydsl query type for ChampionsRound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChampionsRound extends EntityPathBase<ChampionsRound> {

    private static final long serialVersionUID = -11235267L;

    public static final QChampionsRound championsRound = new QChampionsRound("championsRound");

    public final QRound _super = new QRound(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> index = createNumber("index", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    //inherited
    public final NumberPath<Integer> roundSt = _super.roundSt;

    //inherited
    public final EnumPath<RoundStatus> roundStatus = _super.roundStatus;

    //inherited
    public final NumberPath<Integer> season = _super.season;

    public QChampionsRound(String variable) {
        super(ChampionsRound.class, forVariable(variable));
    }

    public QChampionsRound(Path<? extends ChampionsRound> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChampionsRound(PathMetadata metadata) {
        super(ChampionsRound.class, metadata);
    }

}

