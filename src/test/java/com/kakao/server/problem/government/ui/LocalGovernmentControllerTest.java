package com.kakao.server.problem.government.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.server.problem.government.command.application.GovernmentService;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentFileRequest;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentRequest;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.GovernmentListService;
import com.kakao.server.problem.government.query.application.dto.GovernmentListRequest;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value = {SpringExtension.class})
@WebMvcTest(LocalGovernmentController.class)
@AutoConfigureMockMvc
public class LocalGovernmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GovernmentService governmentService;

    @MockBean
    private GovernmentListService governmentListService;

    @Autowired
    private ObjectMapper objectMapper;

    private GovernmentResponse resOne;
    private GovernmentResponse resTwo;

    private LocalGovernmentRequest request;

    private List<GovernmentResponse> responses;

    private List<String> governmentList;

    @BeforeEach
    public void fixture(){

        request = new LocalGovernmentRequest("강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", "8억원 이내", "3% ~ 5%","강원도","춘천지점","강원도 소재 영업점");

        resOne = new GovernmentResponse("강원도","강원도 소재 중소기업으로서 강원도지사가 추천한 자","운전", "8억원 이내", "3% ~ 5%", "강원도","춘천지점","강원도 소재 영업점");
        resTwo = new GovernmentResponse("부천시","부천시 소재 중소기업으로서 부천시장의 추천을 받은 자","운전 및 시설","5억원 이내","1.5%~2.5%","부천시","강서제주지역본부","전 영업점");
        responses = Arrays.asList(resOne, resTwo);

        governmentList = Arrays.asList("안양시", "강원도", "부천시");
    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){

        LocalGovernmentController localGovernmentController = new LocalGovernmentController(governmentService, governmentListService);

        assertThat(localGovernmentController).isNotNull();
    }

    @Test
    @DisplayName("벌크 테스트")
    public void find_bulk_test() throws Exception {

        when(governmentService.bulkInsert(isA(LocalGovernmentFileRequest.class))).thenReturn(1L);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "problem".getBytes());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/government/bulk").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @DisplayName("csv 파싱 실패 테스트")
    public void find_bulk_exception_test() throws Exception {

        when(governmentService.bulkInsert(isA(LocalGovernmentFileRequest.class))).thenThrow(new IOException());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "problem".getBytes());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/government/bulk").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();

    }

    @Test
    @DisplayName("file 미첨부 테스트")
    public void find_bulk_validation_test() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/government/bulk").contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    @DisplayName("업데이트 테스트")
    public void find_update_test() throws Exception {

        when(governmentService.governmentUpdate(isA(LocalGovernmentRequest.class))).thenReturn(resOne);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/government/update").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(request));

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usage").value("운전"))
                .andReturn();

    }

    @Test
    @DisplayName("데이터가 없는 경우 테스트")
    public void find_update_excption_test() throws Exception {

        when(governmentService.governmentUpdate(isA(LocalGovernmentRequest.class))).thenThrow(new NotFoundDataException());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/government/update").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(request));

            mockMvc.perform(builder)
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andReturn();

    }

    @Test
    @DisplayName("필수값이 없는 경우 테스트")
    public void find_update_validation_test() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/government/update").contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    @DisplayName("전체검색 테스트")
    public void find_all_test() throws Exception {

        when(governmentListService.searchGovernmentList()).thenReturn(responses);

        mockMvc.perform(get("/government"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].region").value("강원도"))
                .andReturn();

    }

    @Test
    @DisplayName("Rate 테스트")
    public void find_rate_test() throws Exception {

        when(governmentListService.searchGovernmentRate()).thenReturn(governmentList);

        mockMvc.perform(get("/government/rate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("안양시"))
                .andReturn();

    }

    @Test
    @DisplayName("limited 테스트")
    public void find_limited_test() throws Exception {

        when(governmentListService.searchGovernmentLimited()).thenReturn(governmentList);

        mockMvc.perform(get("/government/limited"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("안양시"))
                .andReturn();

    }

    @Test
    @DisplayName("region 테스트")
    public void find_Region_test() throws Exception {

        when(governmentListService.searchGovernmentRegion(isA(GovernmentListRequest.class))).thenReturn(Optional.of(resOne).get());

        mockMvc.perform(get("/government/region").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"region\":\"강원도\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.region").value("강원도"))
                .andReturn();

    }


}
