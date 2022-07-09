package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectorRecord is a Querydsl query type for DirectorRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectorRecord extends EntityPathBase<DirectorRecord> {

    private static final long serialVersionUID = 792710241L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectorRecord directorRecord = new QDirectorRecord("directorRecord");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.example.newscoccer.domain.director.QDirector director;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<MatchResult> mathResult = createEnum("mathResult", MatchResult.class);

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final com.example.newscoccer.domain.Round.QRound round;

    public final com.example.newscoccer.domain.QTeam team;

    public QDirectorRecord(String variable) {
        this(DirectorRecord.class, forVariable(variable), INITS);
    }

    public QDirectorRecord(Path<? extends DirectorRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectorRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectorRecord(PathMetadata metadata, PathInits inits) {
        this(DirectorRecord.class, metadata, inits);
    }

    public QDirectorRecord(Class<? extends DirectorRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.director = inits.isInitialized("director") ? new com.example.newscoccer.domain.director.QDirector(forProperty("director"), inits.get("director")) : null;
        this.round = inits.isInitialized("round") ? new com.example.newscoccer.domain.Round.QRound(forProperty("round")) : null;
        this.team = inits.isInitialized("team") ? new com.example.newscoccer.domain.QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

