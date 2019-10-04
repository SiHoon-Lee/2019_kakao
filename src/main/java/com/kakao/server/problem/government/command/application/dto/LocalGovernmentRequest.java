package com.kakao.server.problem.government.command.application.dto;

import com.kakao.server.problem.government.command.domain.LimitedAmount;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.Rate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalGovernmentRequest {

    @NotNull
    private String region;

    private String target;

    private String usage;

    private String limit;

    private String rate;

    private String institute;

    private String mgmt;

    private String reception;

    public LocalGovernmentRequest(@NotNull String region, String target, String usage, String limit, String rate, String institute, String mgmt, String reception) {
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limit = limit;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    public LocalGovernment convertToLocalGovernment(){
        return new LocalGovernment(this.target, this.usage, LimitedAmount.parser(this.limit), Rate.parser(this.rate), this.institute, this.mgmt, this.reception);
    }
}
