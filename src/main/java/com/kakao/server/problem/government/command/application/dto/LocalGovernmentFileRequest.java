package com.kakao.server.problem.government.command.application.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalGovernmentFileRequest {

    @NotNull
    private MultipartFile file;

    public LocalGovernmentFileRequest(@NotNull MultipartFile file) {
        this.file = file;
    }
}
