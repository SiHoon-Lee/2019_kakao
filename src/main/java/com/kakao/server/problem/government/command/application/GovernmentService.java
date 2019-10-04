package com.kakao.server.problem.government.command.application;

import com.kakao.server.problem.government.command.application.dto.LocalGovernmentFileRequest;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentRequest;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.command.domain.LocalGovernmentCSV;
import com.kakao.server.problem.government.command.domain.LocalGovernmentRepository;
import com.kakao.server.problem.government.command.domain.LocalGovernmentRepositoryCustom;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class GovernmentService {

    private LocalGovernmentRepository localGovernmentRepository;

    private LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom;

    public GovernmentService(LocalGovernmentRepository localGovernmentRepository, LocalGovernmentRepositoryCustom localGovernmentRepositoryCustom) {
        this.localGovernmentRepository = localGovernmentRepository;
        this.localGovernmentRepositoryCustom = localGovernmentRepositoryCustom;
    }

    @Transactional
    public Long bulkInsert(LocalGovernmentFileRequest localGovernmentFileRequest) throws IOException {

        byte[] bytes = localGovernmentFileRequest.getFile().getBytes();
        CsvToBean<LocalGovernmentCSV> csvToBean = new CsvToBeanBuilder(new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8"))
                .withType(LocalGovernmentCSV.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .build();

        return csvToBean.parse().stream().filter(lg -> {
            LocalGovernment localGovernment = lg.convertToLocalGovernment();
            return localGovernmentRepository.save(localGovernment) != null;
        }).count();
    }

    @Transactional
    public GovernmentResponse governmentUpdate(LocalGovernmentRequest request) throws NotFoundDataException{

        LocalGovernment government = localGovernmentRepositoryCustom.findByRegion(request.getRegion()).orElseThrow(NotFoundDataException::new);
        government.updateField(request.convertToLocalGovernment());

        return localGovernmentRepository.save(government).convertToLocalGovernmentResponse();
    }

}
