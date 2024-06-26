package com.tkbbank.address_service.controllers;

import com.tkbbank.address_service.dto.requests.ManageRequest;
import com.tkbbank.address_service.dto.requests.SuggestionRequest;
import com.tkbbank.address_service.dto.responses.AddressDto;
import com.tkbbank.address_service.dto.responses.GARIdxAddressDto;
import com.tkbbank.address_service.dto.responses.ManageResponse;
import com.tkbbank.address_service.dto.utils.ManageCommand;
import com.tkbbank.address_service.services.AddressService;
import com.tkbbank.address_service.services.LoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/address_service")
@CrossOrigin(originPatterns = "*")
@Log4j2
public class AddressController {

    private final LoaderService loaderService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/manage", consumes = "application/json")
    public ResponseEntity<ManageResponse> manage(@RequestBody ManageRequest request) {
        ManageResponse response = new ManageResponse();
        response.setExecutionStart(LocalDateTime.now());
        try {
            switch (ManageCommand.fromText(request.getCommand())) {
                case LOAD -> {
                    log.info("Start loading");
                    loaderService.processDataTables("TRUNCATE");
                    loaderService.processDataTables("DROP_INDEX");
                    loaderService.processZipFile();
                    loaderService.processDataTables("CREATE_INDEX");
                    loaderService.processIdxTables("CREATE_INDEX");
                    loaderService.processIdxTables("INSERT");
                    loaderService.processIdxTables("UPDATE");
                    loaderService.indexEntities(false);
                    log.info("End loading");
                }
                case INDEX_ENTITIES -> {
                    loaderService.indexEntities(true);
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

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping(path = "/regions")
    public ResponseEntity<List<AddressDto>> getRegions() {
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/json;charset=utf-8"))
                .body(addressService.getRegions().stream().map(address -> modelMapper.map(address, AddressDto.class)).peek(addressDto -> addressDto.setNameType(addressDto.getName() + " " + addressDto.getType())).collect(Collectors.toList()));
    }

    @PostMapping(path = "/suggestions", consumes = "application/json;charset=utf-8")
    public ResponseEntity<List<GARIdxAddressDto>> getSuggestions(@RequestBody SuggestionRequest request) {
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/json;charset=utf-8"))
                .body(addressService.getSuggestions(request.getRegionObjectId(), request.getNamePart()).stream().map(address -> modelMapper.map(address, GARIdxAddressDto.class)).collect(Collectors.toList()));
    }
}