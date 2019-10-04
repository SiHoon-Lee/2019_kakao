package com.kakao.server.problem.government.command.application;


import com.google.common.net.MediaType;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentFileRequest;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentRequest;
import com.kakao.server.problem.government.command.domain.*;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;

@ExtendWith(value = {SpringExtension.class})
@AutoConfigureMockMvc
public class GovernmentServiceTest {

    @MockBean
    private LocalGovernmentRepository localGovernmentRepository;

    @MockBean
    private LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom;

    @InjectMocks
    private GovernmentService governmentService;

    private LocalGovernmentRequest request;

    private LocalGovernment localGovernment;

    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    public void fixture(){

        MockitoAnnotations.initMocks(this);

        mockMultipartFile = new MockMultipartFile("file", "problem.csv", MediaType.CSV_UTF_8.subtype(), "구분,지자체명(기관명),지원대상,용도,지원한도,이차보전,추천기관,관리점,취급점\n1,강릉시,강릉시 소재 중소기업으로서 강릉시장이 추천한 자,운전,추천금액 이내,3%,강릉시,강릉지점,강릉시 소재 영업점".getBytes());
        localGovernment = new LocalGovernment("1","강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", LimitedAmount.parser("8억원 이내"), new Rate(3.0D, 5.0D),"강원도","춘천지점","강원도 소재 영업점");
        request = new LocalGovernmentRequest("강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", "8억원 이내", "3% ~ 5%","강원도","춘천지점","강원도 소재 영업점");
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        governmentService = new GovernmentService(localGovernmentRepository, localGovernmentRepositoryCustom);

        assertThat(governmentService).isNotNull();
    }

    @Test
    @DisplayName("업데이트 성공 테스트")
    public void update_check(){

        when(localGovernmentRepositoryCustom.findByRegion(isA(String.class))).thenReturn(Optional.of(localGovernment));
        when(localGovernmentRepository.save(isA(LocalGovernment.class))).thenReturn(localGovernment);

        GovernmentResponse response = governmentService.governmentUpdate(request);

        assertThat(response).isEqualToComparingFieldByField(localGovernment.convertToLocalGovernmentResponse());
    }

    @Test
    @DisplayName("업데이트 exception 테스트")
    public void update_exception_check(){

        when(localGovernmentRepositoryCustom.findByRegion(isA(String.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundDataException.class, () -> {
            governmentService.governmentUpdate(request);
        });

    }

    @Test
    @DisplayName("업데이트 bulk 테스트")
    public void bulk_check() throws IOException {

        LocalGovernmentFileRequest localGovernmentFileRequest = new LocalGovernmentFileRequest(mockMultipartFile);
        when(localGovernmentRepository.save(isA(LocalGovernment.class))).thenReturn(localGovernment);

        Long result = governmentService.bulkInsert(localGovernmentFileRequest);

        assertThat(result).isEqualTo(1L);

    }

}
