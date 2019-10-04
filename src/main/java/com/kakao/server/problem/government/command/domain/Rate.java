package com.kakao.server.problem.government.command.domain;

import com.kakao.server.problem.government.common.StringParser;
import com.kakao.server.problem.government.common.ValidationCheck;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
@Access(AccessType.FIELD)
public class Rate {

    private static final Double MAX_RATE = 100D;

    @Column(name = "rate_min")
    private Double min;

    @Column(name = "rate_max")
    private Double max;

    @Column(name = "rate_avg")
    private Double avg;

    public Rate() {
        this.min = this.MAX_RATE;
        this.max = this.MAX_RATE;
        this.rateAvg();
    }

    public Rate(Double oneRate) {
        this.min = oneRate;
        this.max = oneRate;
        this.rateAvg();
    }

    public Rate(Double min, Double max) {
        this.min = min;
        this.max = max;
        this.rateAvg();
    }

    private void rateAvg(){
        this.avg = Stream.of(this.min, this.max).mapToDouble(Double::doubleValue).average().getAsDouble();
    }

    public String get(){

        if(this.min.doubleValue() == this.MAX_RATE.doubleValue()) return "대출이자 전액";
        if(this.min.doubleValue() == this.max.doubleValue()) return this.min + "%";

        return String.join(" ~ ", this.min + "%", this.max + "%");
    }

    public static Rate parser(String rate){

        if(ValidationCheck.isEmpty(rate)) return null;

        List<Double> rates = StringParser.ratePaser(rate);

        if( rates.size() == 0 ) return new Rate();
        return rates.size() == 1 ? new Rate(rates.get(0)) : new Rate(rates.get(0), rates.get(1));
    }

}
