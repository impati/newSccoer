package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectorLeagueRecord is a Querydsl query type for DirectorLeagueRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectorLeagueRecord extends EntityPathBase<DirectorLeagueRecord> {

    private static final long serialVersionUID = 1271406448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectorLeagueRecord directorLeagueRecord = new QDirectorLeagueRecord("directorLeagueRecord");

    public final QDirectorRecord _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    // inherited
    public final com.example.newscoccer.domain.director.QDirector director;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate;

    //inherited
    public final EnumPath<MatchResult> mathResult;

    //inherited
    public final NumberPath<Integer> rank;

    // inherited
    public final com.example.newscoccer.domain.Round.QRound round;

    // inherited
    public final com.example.newscoccer.domain.QTeam team;

    public QDirectorLeagueRecord(String variable) {
        this(DirectorLeagueRecord.class, forVariable(variable), INITS);
    }

    public QDirectorLeagueRecord(Path<? extends DirectorLeagueRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectorLeagueRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectorLeagueRecord(PathMetadata metadata, PathInits inits) {
        this(DirectorLeagueRecord.class, metadata, inits);
    }

    public QDirectorLeagueRecord(Class<? extends DirectorLeagueRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDirectorRecord(type, metadata, inits);
        this.createDate = _super.createDate;
        this.director = _super.director;
        this.lastModifiedDate = _super.lastModifiedDate;
        this.mathResult = _super.mathResult;
        this.rank = _super.rank;
        this.round = _super.round;
        this.team = _super.team;
    }

}

