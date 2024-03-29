package com.tkbbank.address_service.controllers;

import com.tkbbank.address_service.dto.requests.ManageRequest;
import com.tkbbank.address_service.dto.requests.SuggestionRequest;
import com.tkbbank.address_service.dto.responses.AddressDto;
import com.tkbbank.address_service.dto.responses.GARIdxAddressDto;
import com.tkbbank.address_service.dto.responses.ManageResponse;
import com.tkbbank.address_service.dto.utils.ManageCommand;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import com.tkbbank.address_service.services.AddressService;
import com.tkbbank.address_service.services.LoaderService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/address_service")
@CrossOrigin(originPatterns = "*")
@Log4j2
public class AddressController {

    @Autowired
    private LoaderService loaderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/manage", produces = "application/json", consumes = "application/json")
    public ManageResponse manage(@RequestBody ManageRequest request) {
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

    @GetMapping(path = "/regions")
    public List<AddressDto> getRegions() {
        List<Address> regions = addressService.getRegions();
        return regions.stream().map(address -> modelMapper.map(address, AddressDto.class)).peek(addressDto -> addressDto.setNameType(addressDto.getName() + " " + addressDto.getType())).collect(Collectors.toList());
    }

    @PostMapping(path = "/suggestions")
    public List<GARIdxAddressDto> getSuggestions(@RequestBody SuggestionRequest request){
        List<? extends GARIdxAddress> suggestions = addressService.getSuggestions(request.getRegionObjectId(), request.getNamePart());
        return suggestions.stream().map(address -> modelMapper.map(address, GARIdxAddressDto.class)).collect(Collectors.toList());
    }
}