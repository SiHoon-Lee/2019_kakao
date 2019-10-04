package com.kakao.server.problem.government.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    private String region;

    public Region(String region) {
        this.region = region;
    }
}
