package com.kakao.server.problem.government.infra;

import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.LocalGovernmentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kakao.server.problem.government.command.domain.QLocalGovernment.localGovernment;
import static com.kakao.server.problem.government.command.domain.QRate.rate;

@Repository
public class JpaLocalGovernmentDataAccess extends QuerydslRepositorySupport implements LocalGovernmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public JpaLocalGovernmentDataAccess(JPAQueryFactory queryFactory) {
        super(LocalGovernment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<LocalGovernment> findByRegion(String region) {
        return Optional.of(queryFactory.selectFrom(localGovernment).where(localGovernment.region.region.eq(region)).fetchFirst());
    }

    @Override
    public List<String> findByOrderByLimitDescAndRateAvgAsc() {
        return queryFactory.select(localGovernment.institute).from(localGovernment).orderBy(localGovernment.lemited.limited.desc() ,rate.min.asc()).fetch();
    }

    @Override
    public List<String> findByOrderByRateAsc() {
        return queryFactory.select(localGovernment.institute).from(localGovernment).orderBy(rate.min.asc()).fetch();
    }

    @Override
    public List<LocalGovernment> findByOrderByRegDtAsc() {
        return queryFactory.selectFrom(localGovernment).orderBy(localGovernment.regDt.asc()).fetch();
    }

}
