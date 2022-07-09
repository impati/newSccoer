package com.example.newscoccer.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectorChampionsRecord is a Querydsl query type for DirectorChampionsRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectorChampionsRecord extends EntityPathBase<DirectorChampionsRecord> {

    private static final long serialVersionUID = -266761205L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectorChampionsRecord directorChampionsRecord = new QDirectorChampionsRecord("directorChampionsRecord");

    public final QDirectorRecord _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    // inherited
    public final com.example.newscoccer.domain.director.QDirector director;

    public final NumberPath<Integer> FirstOrSecond = createNumber("FirstOrSecond", Integer.class);

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

    public QDirectorChampionsRecord(String variable) {
        this(DirectorChampionsRecord.class, forVariable(variable), INITS);
    }

    public QDirectorChampionsRecord(Path<? extends DirectorChampionsRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectorChampionsRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectorChampionsRecord(PathMetadata metadata, PathInits inits) {
        this(DirectorChampionsRecord.class, metadata, inits);
    }

    public QDirectorChampionsRecord(Class<? extends DirectorChampionsRecord> type, PathMetadata metadata, PathInits inits) {
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

