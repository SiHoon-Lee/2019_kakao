package com.kakao.server.problem.government.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringParserTest {


    @Test
    @DisplayName("Null 확인")
    public void limit_parser_null_test(){

        Long result = StringParser.limitPaser("");

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("예외 숫자 확인")
    public void limit_parser_test(){

        Long result = StringParser.limitPaser("10만원");

        assertThat(result).isEqualTo(10L);
    }
}
