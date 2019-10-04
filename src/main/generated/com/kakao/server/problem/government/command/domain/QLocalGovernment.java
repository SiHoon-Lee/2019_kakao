package com.kakao.server.problem.government.command.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocalGovernment is a Querydsl query type for LocalGovernment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocalGovernment extends EntityPathBase<LocalGovernment> {

    private static final long serialVersionUID = -677631376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocalGovernment localGovernment = new QLocalGovernment("localGovernment");

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath institute = createString("institute");

    public final QLimitedAmount lemited;

    public final StringPath mgmt = createString("mgmt");

    public final StringPath purpose = createString("purpose");

    public final QRate rate;

    public final StringPath reception = createString("reception");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final QRegion region;

    public final StringPath target = createString("target");

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QLocalGovernment(String variable) {
        this(LocalGovernment.class, forVariable(variable), INITS);
    }

    public QLocalGovernment(Path<? extends LocalGovernment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocalGovernment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocalGovernment(PathMetadata metadata, PathInits inits) {
        this(LocalGovernment.class, metadata, inits);
    }

    public QLocalGovernment(Class<? extends LocalGovernment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lemited = inits.isInitialized("lemited") ? new QLimitedAmount(forProperty("lemited")) : null;
        this.rate = inits.isInitialized("rate") ? new QRate(forProperty("rate")) : null;
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
    }

}

