package com.kakao.server.problem.government.query.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GovernmentListRequest implements Serializable {

    @NotNull
    private String region;

    public GovernmentListRequest(String region) {
        this.region = region;
    }
}
