package com.kakao.server.problem.government.command.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalGovernmentFileRequestTest {

    @Test
    @DisplayName("File 테스트")
    public void file_check(){

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "problem".getBytes());

        LocalGovernmentFileRequest localGovernmentFileRequest = new LocalGovernmentFileRequest(mockMultipartFile);

        assertThat(localGovernmentFileRequest.getFile()).isNotNull();
        assertThat(localGovernmentFileRequest.getFile().getName()).isEqualTo("file");

    }

    @Test
    @DisplayName("생성자 테스트")
    public void constructor_check(){
        assertThat(new LocalGovernmentFileRequest()).isNotNull();
    }
}
