package com.tkbbank.address_service.controllers;

import com.tkbbank.address_service.dto.requests.ManageRequest;
import com.tkbbank.address_service.dto.responses.ManageResponse;
import com.tkbbank.address_service.dto.utils.ManageCommand;
import com.tkbbank.address_service.services.LoaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/address_service")
@CrossOrigin(originPatterns = "*")
@Log4j2
public class AddressController {

    @Autowired
    private LoaderService loaderService;

    @PostMapping(path = "/manage", produces = "application/json", consumes = "application/json")
    public ManageResponse manage(@RequestBody ManageRequest request) {
        ManageResponse response = new ManageResponse();
        response.setExecutionStart(LocalDateTime.now());
        try {
            switch (ManageCommand.fromText(request.getCommand())) {
                case LOAD -> {
                    log.info("Start loading");
                    loaderService.truncateAllTables();
                    loaderService.dropAllIndexes();
                    loaderService.processZipFile();
                    loaderService.createAllIndexes();
                    log.info("End loading");
                }
            }
            response.setErrorCode(0);
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrorMessage(e.getMessage());
            response.setErrorCode(-1);
        } finally {
            response.setExecutionEnd(LocalDateTime.now());
        }
        return response;
    }
}