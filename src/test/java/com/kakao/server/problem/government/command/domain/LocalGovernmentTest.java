package com.kakao.server.problem.government.command.domain;

import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalGovernmentTest {

    private LocalGovernment localGovernment;
    private Rate rate;

    @BeforeEach
    public void fixture(){

        rate = new Rate(3.0D, 5.0D);
        localGovernment = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", new LimitedAmount(8L, "8억원 이내"), rate,"강원도","춘천지점","강원도 소재 영업점");
    }

    @Test
    @DisplayName("Field 테스트")
    public void field_test(){

        LocalGovernment result = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", new LimitedAmount(8L, "8억원 이내"), rate,"강원도","춘천지점","강원도 소재 영업점");
        assertThat(result).isEqualToComparingFieldByFieldRecursively(localGovernment);
    }

    @Test
    @DisplayName("Rate 조합 테스트")
    public void response_rate_test(){

        GovernmentResponse governmentResponse = localGovernment.convertToLocalGovernmentResponse();
        assertThat(governmentResponse.getRate()).isEqualTo("3.0% ~ 5.0%");
    }

    @DisplayName("업데이트 테스트")
    @ParameterizedTest
    @CsvSource({"2,강원도,속초시 소재 중소기업으로서 속초시장의 융자추천을 받은 중소기업,운전,3억원 이내,3.00%,속초시,속초지점,속초시 소재 영업점"})
    public void update_test(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        LocalGovernment updateRequest = localGovernmentCSV.convertToLocalGovernment();

        localGovernment.updateField(updateRequest);

        assertThat(localGovernment.getRegion().getRegion()).isEqualTo(updateRequest.getRegion().getRegion());
        assertThat(localGovernment.getInstitute()).isEqualTo(updateRequest.getInstitute());
        assertThat(localGovernment.getPurpose()).isEqualTo(updateRequest.getPurpose());
        assertThat(localGovernment.getMgmt()).isEqualTo(updateRequest.getMgmt());
        assertThat(localGovernment.getReception()).isEqualTo(updateRequest.getReception());
        assertThat(localGovernment.getRate()).isEqualToComparingFieldByFieldRecursively(updateRequest.getRate());
        assertThat(localGovernment.getLemited()).isEqualToComparingFieldByFieldRecursively(updateRequest.getLemited());
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new LocalGovernment()).isNotNull();
    }
}