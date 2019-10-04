package com.kakao.server.problem.government.ui;

import com.kakao.server.problem.government.command.application.GovernmentService;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentFileRequest;
import com.kakao.server.problem.government.command.application.dto.LocalGovernmentRequest;
import com.kakao.server.problem.government.command.domain.LocalGovernment;
import com.kakao.server.problem.government.exception.NotFoundDataException;
import com.kakao.server.problem.government.query.application.GovernmentListService;
import com.kakao.server.problem.government.query.application.dto.GovernmentListRequest;
import com.kakao.server.problem.government.query.application.dto.GovernmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/government")
public class LocalGovernmentController {

    private GovernmentService governmentService;

    private GovernmentListService governmentListService;

    public LocalGovernmentController(GovernmentService governmentService, GovernmentListService governmentListService) {
        this.governmentService = governmentService;
        this.governmentListService = governmentListService;
    }

    @PostMapping(value = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity infomationBulk(@Valid LocalGovernmentFileRequest localGovernmentFileRequest){

        try {
            governmentService.bulkInsert(localGovernmentFileRequest);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity updateGovernment(@RequestBody @Valid LocalGovernmentRequest localGovernmentRequest) {

        GovernmentResponse governmentResponse;
        try {
            governmentResponse = governmentService.governmentUpdate(localGovernmentRequest);
        } catch (NotFoundDataException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(governmentResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LocalGovernment>> searchGovernment(){
        return new ResponseEntity(governmentListService.searchGovernmentList(), HttpStatus.OK);
    }

    @GetMapping("/region")
    public ResponseEntity<GovernmentResponse> searchGovernmentByRegion(@RequestBody @Valid GovernmentListRequest governmentListRequest){
        return new ResponseEntity(governmentListService.searchGovernmentRegion(governmentListRequest), HttpStatus.OK);
    }

    @GetMapping("/rate")
    public ResponseEntity<String> searchGovernmentByRate(){
        return new ResponseEntity(governmentListService.searchGovernmentRate(), HttpStatus.OK);
    }

    @GetMapping("/limited")
    public ResponseEntity<String> searchGovernmentByLimited(){
        return new ResponseEntity(governmentListService.searchGovernmentLimited(), HttpStatus.OK);
    }

}
