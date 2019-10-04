package com.kakao.server.problem.government.query.application.dto;

import com.kakao.server.problem.government.command.domain.LimitedAmount;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GovernmentResponseTest {

    private GovernmentResponse response;
    private LocalGovernment localGovernment;

    @BeforeEach
    public void fixture(){

        localGovernment = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", LimitedAmount.parser("8억원 이내"), new Rate(3.0D, 5.0D),"강원도","춘천지점","강원도 소재 영업점");
        response = localGovernment.convertToLocalGovernmentResponse();
    }

    @Test
    @DisplayName("Response 테스트")
    public void data_check(){

        assertThat(response.getRegion()).isEqualTo(localGovernment.getRegion().getRegion());
        assertThat(response.getInstitute()).isEqualTo(localGovernment.getInstitute());
        assertThat(response.getReception()).isEqualTo(localGovernment.getReception());
        assertThat(response.getTarget()).isEqualTo(localGovernment.getTarget());
        assertThat(response.getUsage()).isEqualTo(localGovernment.getPurpose());
        assertThat(response.getLimit()).isEqualTo(localGovernment.getLemited().getLimitedWord());
        assertThat(response.getMgmt()).isEqualTo(localGovernment.getMgmt());
        assertThat(response.getRate()).isEqualTo(localGovernment.getRate().get());
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new GovernmentResponse()).isNotNull();
    }

}
