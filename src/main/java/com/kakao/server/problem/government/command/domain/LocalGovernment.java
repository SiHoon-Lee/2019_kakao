package com.kakao.server.problem.government.command.domain;

import com.kakao.server.problem.government.common.ValidationCheck;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@DynamicUpdate
@Table(name = "government")
@SecondaryTable(
        name = "region",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "code")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalGovernment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @AttributeOverrides({
            @AttributeOverride(name = "government_region",
                    column = @Column(table = "region"))
    })
    @Embedded
    private Region region;

    private String target;

    private String purpose;

    @Embedded
    private LimitedAmount lemited;

    @Embedded
    private Rate rate;

    private String institute;

    private String mgmt;

    private String reception;

    @CreationTimestamp
    private LocalDateTime regDt;

    @UpdateTimestamp
    private LocalDateTime updDt;

    public LocalGovernment(String code, String region, String target, String purpose, LimitedAmount lemited, Rate rate, String institute, String mgmt, String reception) {
        this.code = "reg" + code;
        this.region = new Region(region);
        this.target = target;
        this.purpose = purpose;
        this.lemited = lemited;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    public LocalGovernment(String target, String purpose, LimitedAmount lemited, Rate rate, String institute, String mgmt, String reception) {
        this.target = target;
        this.purpose = purpose;
        this.lemited = lemited;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    private String getConvertLimite(){
        return this.lemited.getLimitedWord();
    }

    public void updateField(LocalGovernment localGovernment){

        if(ValidationCheck.isNotEmpty(localGovernment.getTarget())){
            this.target = localGovernment.getTarget();
        }

        if(ValidationCheck.isNotEmpty(localGovernment.getPurpose())){
            this.purpose = localGovernment.getPurpose();
        }

        if(ValidationCheck.isNotNull(localGovernment.getLemited())){
            this.lemited = localGovernment.getLemited();
        }

        if(ValidationCheck.isNotNull(localGovernment.getRate())){
            this.rate = localGovernment.getRate();
        }

        if(ValidationCheck.isNotEmpty(localGovernment.getInstitute())){
            this.institute = localGovernment.getInstitute();
        }

        if(ValidationCheck.isNotEmpty(localGovernment.getMgmt())){
            this.mgmt = localGovernment.getMgmt();
        }

        if(ValidationCheck.isNotEmpty(localGovernment.getReception())){
            this.reception = localGovernment.getReception();
        }
    }

    public GovernmentResponse convertToLocalGovernmentResponse(){
        GovernmentResponse localGovernmentResponse = new GovernmentResponse(this.region.getRegion(), this.target, this.purpose ,this.getConvertLimite(), this.rate.get(), this.institute, this.mgmt, this.reception);
        return localGovernmentResponse;
    }
}