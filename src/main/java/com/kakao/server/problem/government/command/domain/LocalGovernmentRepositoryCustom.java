package com.kakao.server.problem.government.command.domain;

import java.util.List;
import java.util.Optional;

public interface LocalGovernmentRepositoryCustom {

    Optional<LocalGovernment> findByRegion(String region);

    List<String> findByOrderByLimitDescAndRateAvgAsc();

    List<String> findByOrderByRateAsc();

    List<LocalGovernment> findByOrderByRegDtAsc();


}
