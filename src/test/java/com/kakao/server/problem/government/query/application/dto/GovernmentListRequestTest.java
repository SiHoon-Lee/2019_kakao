package com.kakao.server.problem.government.query.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GovernmentListRequestTest {


    @Test
    @DisplayName("Response 테스트")
    public void data_check(){

        String region = "강원도";

        GovernmentListRequest governmentListRequest = new GovernmentListRequest(region);

        assertThat(governmentListRequest.getRegion()).isEqualTo(region);
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new GovernmentListRequest()).isNotNull();
    }

}
