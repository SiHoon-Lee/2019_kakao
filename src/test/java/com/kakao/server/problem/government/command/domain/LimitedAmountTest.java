package com.kakao.server.problem.government.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LimitedAmountTest {

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new LimitedAmount()).isNotNull();
    }

    @Test
    @DisplayName("args 생성자 테스트")
    public void constructor_args_check(){

        assertThat(new LimitedAmount("추천금액 이내")).isNotNull();
    }

    @Test
    @DisplayName("문자열(1자리)을 숫자로 변환")
    public void limited_word_test(){

        Long million = LimitedAmount.parser("1백만원").getLimited();
        Long hundredMillion = LimitedAmount.parser("1억원").getLimited();

        assertThat(million).isEqualTo(1000000);
        assertThat(hundredMillion).isEqualTo(100000000);
    }

    @Test
    @DisplayName("문자열(2자리)을 숫자로 변환")
    public void limited_2_word_test(){

        Long million = LimitedAmount.parser("20백만원").getLimited();
        Long hundredMillion = LimitedAmount.parser("20억원").getLimited();

        assertThat(million).isEqualTo(20000000);
        assertThat(hundredMillion).isEqualTo(2000000000);
    }

    @Test
    @DisplayName("예외 문자열 처리")
    public void limited_order_word_test(){

        Long order = LimitedAmount.parser("추천금액 이내").getLimited();

        assertThat(order).isEqualTo(999900000000L);
    }

}
