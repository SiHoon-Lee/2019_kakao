package com.kakao.server.problem.government.command.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRate is a Querydsl query type for Rate
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRate extends BeanPath<Rate> {

    private static final long serialVersionUID = -312518996L;

    public static final QRate rate = new QRate("rate");

    public final NumberPath<Double> avg = createNumber("avg", Double.class);

    public final NumberPath<Double> max = createNumber("max", Double.class);

    public final NumberPath<Double> min = createNumber("min", Double.class);

    public QRate(String variable) {
        super(Rate.class, forVariable(variable));
    }

    public QRate(Path<? extends Rate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRate(PathMetadata metadata) {
        super(Rate.class, metadata);
    }

}

