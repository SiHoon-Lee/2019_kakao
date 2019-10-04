package com.kakao.server.problem.government.command.domain;

import com.opencsv.bean.CsvBindByPosition;

public class LocalGovernmentCSV {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private String region;

    @CsvBindByPosition(position = 2)
    private String target;

    @CsvBindByPosition(position = 3)
    private String usage;

    @CsvBindByPosition(position = 4)
    private String limit;

    @CsvBindByPosition(position = 5)
    private String rate;

    @CsvBindByPosition(position = 6)
    private String institute;

    @CsvBindByPosition(position = 7)
    private String mgmt;

    @CsvBindByPosition(position = 8)
    private String reception;

    public LocalGovernmentCSV() {
    }

    public LocalGovernmentCSV(String id, String region, String target, String usage, String limit, String rate, String institute, String mgmt, String reception) {
        this.id = id;
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
        return new LocalGovernment(this.id, this.region, this.target, this.usage, LimitedAmount.parser(this.limit), Rate.parser(this.rate), this.institute, this.mgmt, this.reception);
    }

}
