package com.kakao.server.problem.government.command.domain;

import com.kakao.server.problem.government.common.StringParser;
import com.kakao.server.problem.government.common.ValidationCheck;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@Access(AccessType.FIELD)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LimitedAmount {

    private static final Long RECOMMEND_AMOUNT = 9999L * 10000 * 10000;

    private Long limited;

    private String limitedWord;

    public LimitedAmount(String limitedWord) {
        this.limited = this.RECOMMEND_AMOUNT;
        this.limitedWord = limitedWord;
    }

    public LimitedAmount(Long limited, String limitedWord) {
        this.limited = limited;
        this.limitedWord = limitedWord;
    }

    public static LimitedAmount parser(String limit){

        if(ValidationCheck.isEmpty(limit)) return null;

        Long amount = StringParser.limitPaser(limit);
        if(amount != null){
            return new LimitedAmount(amount, limit);
        }

        return new LimitedAmount(limit);
    }

}
