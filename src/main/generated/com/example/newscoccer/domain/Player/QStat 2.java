package com.example.newscoccer.domain.Player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStat is a Querydsl query type for Stat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStat extends EntityPathBase<Stat> {

    private static final long serialVersionUID = -1600405496L;

    public static final QStat stat = new QStat("stat");

    public final com.example.newscoccer.domain.QBaseEntity _super = new com.example.newscoccer.domain.QBaseEntity(this);

    public final NumberPath<Integer> acceleration = createNumber("acceleration", Integer.class);

    public final NumberPath<Integer> activeness = createNumber("activeness", Integer.class);

    public final NumberPath<Integer> balance = createNumber("balance", Integer.class);

    public final NumberPath<Integer> ballControl = createNumber("ballControl", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> crosses = createNumber("crosses", Integer.class);

    public final NumberPath<Integer> defense = createNumber("defense", Integer.class);

    public final NumberPath<Integer> diving = createNumber("diving", Integer.class);

    public final NumberPath<Integer> dribble = createNumber("dribble", Integer.class);

    public final NumberPath<Integer> goalDetermination = createNumber("goalDetermination", Integer.class);

    public final NumberPath<Integer> goalKick = createNumber("goalKick", Integer.class);

    public final NumberPath<Integer> handling = createNumber("handling", Integer.class);

    public final NumberPath<Integer> heading = createNumber("heading", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> intercepting = createNumber("intercepting", Integer.class);

    public final NumberPath<Integer> jump = createNumber("jump", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> longPass = createNumber("longPass", Integer.class);

    public final NumberPath<Integer> midRangeShot = createNumber("midRangeShot", Integer.class);

    public final NumberPath<Integer> pass = createNumber("pass", Integer.class);

    public final NumberPath<Integer> physicalFight = createNumber("physicalFight", Integer.class);

    public final NumberPath<Integer> positioning = createNumber("positioning", Integer.class);

    public final NumberPath<Integer> sense = createNumber("sense", Integer.class);

    public final NumberPath<Integer> shootPower = createNumber("shootPower", Integer.class);

    public final NumberPath<Integer> slidingTackle = createNumber("slidingTackle", Integer.class);

    public final NumberPath<Integer> speed = createNumber("speed", Integer.class);

    public final NumberPath<Integer> speedReaction = createNumber("speedReaction", Integer.class);

    public final NumberPath<Integer> stamina = createNumber("stamina", Integer.class);

    public final NumberPath<Integer> tackle = createNumber("tackle", Integer.class);

    public final NumberPath<Integer> visualRange = createNumber("visualRange", Integer.class);

    public QStat(String variable) {
        super(Stat.class, forVariable(variable));
    }

    public QStat(Path<? extends Stat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStat(PathMetadata metadata) {
        super(Stat.class, metadata);
    }

}

