package com.kakao.server.problem.government.command.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLimitedAmount is a Querydsl query type for LimitedAmount
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QLimitedAmount extends BeanPath<LimitedAmount> {

    private static final long serialVersionUID = 706785862L;

    public static final QLimitedAmount limitedAmount = new QLimitedAmount("limitedAmount");

    public final NumberPath<Long> limited = createNumber("limited", Long.class);

    public final StringPath limitedWord = createString("limitedWord");

    public QLimitedAmount(String variable) {
        super(LimitedAmount.class, forVariable(variable));
    }

    public QLimitedAmount(Path<? extends LimitedAmount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLimitedAmount(PathMetadata metadata) {
        super(LimitedAmount.class, metadata);
    }

}

