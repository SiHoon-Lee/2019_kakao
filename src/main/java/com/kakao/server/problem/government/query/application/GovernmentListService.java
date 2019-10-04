package com.kakao.server.problem.government.query.application;

import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.LocalGovernmentRepositoryCustom;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.dto.GovernmentListRequest;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GovernmentListService {

    private LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom;

    public GovernmentListService(LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom) {
        this.localGovernmentRepositoryCustom = localGovernmentRepositoryCustom;
    }

    public List<GovernmentResponse> searchGovernmentList(){
        return localGovernmentRepositoryCustom.findByOrderByRegDtAsc().stream().map(LocalGovernment::convertToLocalGovernmentResponse).collect(Collectors.toList());
    }

    public GovernmentResponse searchGovernmentRegion(GovernmentListRequest governmentListRequest){
        return localGovernmentRepositoryCustom.findByRegion(governmentListRequest.getRegion()).map(LocalGovernment::convertToLocalGovernmentResponse)
                .orElseThrow(NotFoundDataException::new);
    }

    public List<String> searchGovernmentRate(){
        return localGovernmentRepositoryCustom.findByOrderByRateAsc();
    }

    public List<String> searchGovernmentLimited(){
        return localGovernmentRepositoryCustom.findByOrderByLimitDescAndRateAvgAsc();
    }

}
