package com.kakao.server.problem.government.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalGovernmentCSVTest {

    @DisplayName("생성자 테스트")
    @Test
    public void constructor_check(){
        assertThat(new LocalGovernmentCSV()).isNotNull();
    }

    @DisplayName("Value 테스트")
    @ParameterizedTest
    @CsvSource({"1,강릉시,강릉시 소재 중소기업으로서 강릉시장이 추천한 자,운전,추천금액 이내,3%,강릉시,강릉지점,강릉시 소재 영업점"})
    public void object_value_check(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        LocalGovernmentCSV csv = new LocalGovernmentCSV("1", "강릉시","강릉시 소재 중소기업으로서 강릉시장이 추천한 자","운전","추천금액 이내","3%","강릉시","강릉지점","강릉시 소재 영업점");

        assertThat(localGovernmentCSV).isEqualToComparingFieldByField(csv);
    }

    @DisplayName("Rate 생성자 테스트")
    @Test
    public void rate_constructor_check(){
        assertThat(new Rate()).isNotNull();
    }

    @DisplayName("이차보전 단일 테스트")
    @ParameterizedTest
    @CsvSource({"1,강릉시,강릉시 소재 중소기업으로서 강릉시장이 추천한 자,운전,추천금액 이내,3%,강릉시,강릉지점,강릉시 소재 영업점"})
    public void rate_check(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        Rate rate = localGovernmentCSV.convertToLocalGovernment().getRate();

        assertThat(rate).isEqualToComparingFieldByField(new Rate(3D));
    }

    @DisplayName("이차보전 다중 테스트")
    @ParameterizedTest
    @CsvSource({"2,강원도,강원도 소재 중소기업으로서 강원도지사가 추천한 자,운전,8억원 이내,3%~5%,강원도,춘천지점,강원도 소재 영업점"})
    public void multi_rate_check(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        Rate rate = localGovernmentCSV.convertToLocalGovernment().getRate();

        assertThat(rate).isEqualToComparingFieldByField(new Rate(3D, 5D));
    }

    @DisplayName("이차보전 소수점 테스트")
    @ParameterizedTest
    @CsvSource({"2,강원도,강원도 소재 중소기업으로서 강원도지사가 추천한 자,운전,8억원 이내,3.0%~5.0%,강원도,춘천지점,강원도 소재 영업점"})
    public void decimal_rate_check(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        Rate rate = localGovernmentCSV.convertToLocalGovernment().getRate();

        assertThat(rate).isEqualToComparingFieldByField(new Rate(3.0D, 5.0D));
    }

    @DisplayName("convert 테스트")
    @ParameterizedTest
    @CsvSource({"2,강원도,강원도 소재 중소기업으로서 강원도지사가 추천한 자,운전,8억원 이내,3.0%~5.0%,강원도,춘천지점,강원도 소재 영업점"})
    public void convert_check(@AggregateWith(LocalGovernmentCSVAggregator.class) final LocalGovernmentCSV localGovernmentCSV){

        LocalGovernment localGovernment = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", LimitedAmount.parser("8억원 이내"), new Rate(3.0D, 5.0D),"강원도","춘천지점","강원도 소재 영업점");
        LocalGovernment result = localGovernmentCSV.convertToLocalGovernment();

        assertThat(result).isNotNull();
        assertThat(result.getInstitute()).isEqualTo(localGovernment.getInstitute());
        assertThat(result.getReception()).isEqualTo(localGovernment.getReception());
        assertThat(result.getTarget()).isEqualTo(localGovernment.getTarget());
        assertThat(result.getPurpose()).isEqualTo(localGovernment.getPurpose());
        assertThat(result.getLemited().getLimitedWord()).isEqualTo(localGovernment.getLemited().getLimitedWord());
        assertThat(result.getMgmt()).isEqualTo(localGovernment.getMgmt());
        assertThat(result.getRate().get()).isEqualTo(localGovernment.getRate().get());

    }

}