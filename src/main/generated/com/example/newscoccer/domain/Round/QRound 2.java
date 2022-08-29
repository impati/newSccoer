package com.example.newscoccer.domain.Round;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRound is a Querydsl query type for Round
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRound extends EntityPathBase<Round> {

    private static final long serialVersionUID = 520454069L;

    public static final QRound round = new QRound("round");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> roundSt = createNumber("roundSt", Integer.class);

    public final EnumPath<RoundStatus> roundStatus = createEnum("roundStatus", RoundStatus.class);

    public final NumberPath<Integer> season = createNumber("season", Integer.class);

    public QRound(String variable) {
        super(Round.class, forVariable(variable));
    }

    public QRound(Path<? extends Round> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRound(PathMetadata metadata) {
        super(Round.class, metadata);
    }

}

