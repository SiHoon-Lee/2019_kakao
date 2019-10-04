package com.kakao.server.problem.government.query.application;

import com.kakao.server.problem.government.command.domain.LimitedAmount;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.LocalGovernmentRepositoryCustom;
import com.kakao.server.problem.government.command.domain.Rate;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.dto.GovernmentListRequest;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(value = {SpringExtension.class})
@AutoConfigureMockMvc
public class GovernmentListServiceTest {

    @MockBean
    private LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom;

    @InjectMocks
    private GovernmentListService governmentListService;

    private LocalGovernment resOne;
    private LocalGovernment resTwo;

    @BeforeEach
    public void fixture(){

        MockitoAnnotations.initMocks(this);

        resOne = new LocalGovernment("1","강릉시","강릉시 소재 중소기업으로서 강릉시장이 추천한 자","운전", new LimitedAmount(8L, "8억원 이내"), new Rate(3.0D),"강릉시","강릉지점","강릉시 소재 영업점");
        resTwo = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", new LimitedAmount(8L, "8억원 이내"), new Rate(3.0D, 5.0D),"강원도","춘천지점","강원도 소재 영업점");
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        governmentListService = new GovernmentListService(localGovernmentRepositoryCustom);

        assertThat(governmentListService).isNotNull();
    }

    @Test
    @DisplayName("기본 검색 테스트")
    public void searchGovernmentList_check(){

        when(localGovernmentRepositoryCustom.findByOrderByRegDtAsc()).thenReturn(Arrays.asList(resOne, resTwo));

        List<GovernmentResponse> responseList = governmentListService.searchGovernmentList();

        assertThat(responseList).containsAnyOf(resOne.convertToLocalGovernmentResponse(), resTwo.convertToLocalGovernmentResponse());
    }

    @Test
    @DisplayName("기본 결과가 없는 경우 테스트")
    public void searchGovernmentList_empty_check(){

        when(localGovernmentRepositoryCustom.findByOrderByRegDtAsc()).thenReturn(Arrays.asList());

        List<GovernmentResponse> responseList = governmentListService.searchGovernmentList();

        assertThat(responseList).isEqualTo(Arrays.asList());
    }

    @Test
    @DisplayName("이차보전 검색 테스트")
    public void searchGovernmentRate_check(){

        when(localGovernmentRepositoryCustom.findByOrderByRateAsc()).thenReturn(Arrays.asList(resOne.getRegion().getRegion(), resTwo.getRegion().getRegion()));

        List<String> responseList = governmentListService.searchGovernmentRate();

        assertThat(responseList).containsAnyOf(resOne.getRegion().getRegion(), resTwo.getRegion().getRegion());
    }

    @Test
    @DisplayName("이차보전 결과가 없는 경우 테스트")
    public void searchGovernmentRate_empty_check(){

        when(localGovernmentRepositoryCustom.findByOrderByRateAsc()).thenReturn(Arrays.asList());

        List<String> responseList = governmentListService.searchGovernmentRate();

        assertThat(responseList).isEqualTo(Arrays.asList());
    }

    @Test
    @DisplayName("지원한도 검색 테스트")
    public void searchGovernmentLimited_check(){

        when(localGovernmentRepositoryCustom.findByOrderByLimitDescAndRateAvgAsc()).thenReturn(Arrays.asList(resOne.getRegion().getRegion(), resTwo.getRegion().getRegion()));

        List<String> responseList = governmentListService.searchGovernmentLimited();

        assertThat(responseList).containsAnyOf(resOne.getRegion().getRegion(), resTwo.getRegion().getRegion());
    }

    @Test
    @DisplayName("지원한도 결과가 없는 경우 테스트")
    public void searchGovernmentLimited_empty_check(){

        when(localGovernmentRepositoryCustom.findByOrderByLimitDescAndRateAvgAsc()).thenReturn(Arrays.asList());

        List<String> responseList = governmentListService.searchGovernmentLimited();

        assertThat(responseList).isEqualTo(Arrays.asList());
    }

    @Test
    @DisplayName("기관명 검색 테스트")
    public void searchGovernmentRegion_check(){

        when(localGovernmentRepositoryCustom.findByRegion(isA(String.class))).thenReturn(Optional.ofNullable(resOne));

        GovernmentResponse response = governmentListService.searchGovernmentRegion(new GovernmentListRequest("강릉시"));

        assertThat(response).isEqualTo(resOne.convertToLocalGovernmentResponse());
    }

    @Test
    @DisplayName("기관명 없는 경우 테스트")
    public void searchGovernmentRegion_empty_check(){

        when(localGovernmentRepositoryCustom.findByRegion(isA(String.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(NotFoundDataException.class, () -> {
            governmentListService.searchGovernmentRegion(new GovernmentListRequest("강릉시"));
        });
    }

}
