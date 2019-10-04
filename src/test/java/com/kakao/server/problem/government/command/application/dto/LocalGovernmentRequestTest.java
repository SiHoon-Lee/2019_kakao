package com.kakao.server.problem.government.command.application.dto;

import com.kakao.server.problem.government.command.domain.LimitedAmount;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalGovernmentRequestTest {


    private LocalGovernmentRequest request;

    @BeforeEach
    public void fixture(){

        request = new LocalGovernmentRequest("강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", "8억원 이내", "3% ~ 5%","강원도","춘천지점","강원도 소재 영업점");
    }

    @Test
    @DisplayName("request 테스트")
    public void data_check(){

        assertThat(request.getRegion()).isEqualTo("강원도");
        assertThat(request.getTarget()).isEqualTo("강원도 소재 중소기업으로서 강원도지사가 추천한 자");
        assertThat(request.getUsage()).isEqualTo("운전");
        assertThat(request.getLimit()).isEqualTo("8억원 이내");
        assertThat(request.getRate()).isEqualTo("3% ~ 5%");
        assertThat(request.getInstitute()).isEqualTo("강원도");
        assertThat(request.getMgmt()).isEqualTo("춘천지점");
        assertThat(request.getReception()).isEqualTo("강원도 소재 영업점");
    }

    @Test
    @DisplayName("convert 테스트")
    public void convert_check(){

        LocalGovernment localGovernment = request.convertToLocalGovernment();

        assertThat(localGovernment.getTarget()).isEqualTo(request.getTarget());
        assertThat(localGovernment.getPurpose()).isEqualTo(request.getUsage());
        assertThat(localGovernment.getInstitute()).isEqualTo(request.getInstitute());
        assertThat(localGovernment.getMgmt()).isEqualTo(request.getMgmt());
        assertThat(localGovernment.getReception()).isEqualTo(request.getReception());
        assertThat(localGovernment.getLemited()).isEqualToComparingFieldByField(LimitedAmount.parser("8억원 이내"));
        assertThat(localGovernment.getRate()).isEqualToComparingFieldByField(Rate.parser("3% ~ 5%"));
    }


    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new LocalGovernmentRequest()).isNotNull();
    }

}
