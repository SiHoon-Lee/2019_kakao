package com.kakao.server.problem.government.infra;


import com.kakao.server.problem.government.command.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest
public class JpaLocalGovernmentDataAccessTest {

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    @Autowired
    private LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom;

    private LocalGovernment localGovernment;

    @BeforeEach
    public void fixture(){
        Rate rate = new Rate(3.0D, 5.0D);
        localGovernment = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", new LimitedAmount(8L, "8억원 이내"), rate,"강원도","춘천지점","강원도 소재 영업점");
        localGovernmentRepository.save(localGovernment);
    }

    @AfterEach
    public void deleteData(){
        localGovernmentRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("기관명으로 찾기")
    public void findByRegion_test(){

        Optional<LocalGovernment> government = localGovernmentRepositoryCustom.findByRegion("강원도");

        assertThat(government.get().getLemited()).isEqualTo(localGovernment.getLemited());
        assertThat(government.get().getRegion().getRegion()).isEqualTo(localGovernment.getRegion().getRegion());
        assertThat(government.get().getMgmt()).isEqualTo(localGovernment.getMgmt());
        assertThat(government.get().getInstitute()).isEqualTo(localGovernment.getInstitute());
        assertThat(government.get().getRate()).isEqualToComparingFieldByField(localGovernment.getRate());
    }

    @Test
    @DisplayName("이차보전 기준 정렬")
    public void findByOrderByLimitDescAndRateAvgAsc_test(){

        List<String> governments = localGovernmentRepositoryCustom.findByOrderByLimitDescAndRateAvgAsc();


        assertThat(governments.size()).isEqualTo(1);
        assertThat(governments).isEqualTo(Arrays.asList(localGovernment.getRegion().getRegion()));
    }

    @Test
    @DisplayName("등록일 기준 정렬")
    public void findByOrderByRegDtAsc_test(){

        List<LocalGovernment> governments = localGovernmentRepositoryCustom.findByOrderByRegDtAsc();

        assertThat(governments.size()).isEqualTo(1);
        assertThat(governments.get(0)).isNotNull();
        assertThat(governments.get(0).getLemited()).isEqualTo(localGovernment.getLemited());
        assertThat(governments.get(0).getRegion().getRegion()).isEqualTo(localGovernment.getRegion().getRegion());
        assertThat(governments.get(0).getMgmt()).isEqualTo(localGovernment.getMgmt());
        assertThat(governments.get(0).getInstitute()).isEqualTo(localGovernment.getInstitute());
        assertThat(governments.get(0).getRate()).isEqualToComparingFieldByField(localGovernment.getRate());
    }

    @Test
    @DisplayName("등록일 기준 정렬")
    public void findByOrderByRateAsc_test(){

        List<String> governments = localGovernmentRepositoryCustom.findByOrderByRateAsc();

        assertThat(governments.size()).isEqualTo(1);
        assertThat(governments).isEqualTo(Arrays.asList(localGovernment.getRegion().getRegion()));
    }

}
