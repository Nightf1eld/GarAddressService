package com.tkbbank.address_service.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkbbank.address_service.dto.requests.ManageRequest;
import com.tkbbank.address_service.dto.requests.SuggestionRequest;
import com.tkbbank.address_service.dto.utils.ManageCommand;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import com.tkbbank.address_service.services.AddressService;
import com.tkbbank.address_service.services.LoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTests {

    @Mock
    private LoaderService loaderService;

    @Mock
    private AddressService addressService;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressController addressController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> validManageCommandEnumValues = List.of("LOAD", "INDEX_ENTITIES");
    private ManageRequest manageRequest;
    private SuggestionRequest suggestionRequest;
    private Address address;
    private GARIdxAddress garIdxAddress;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
        manageRequest = ManageRequest.builder().command(validManageCommandEnumValues.get(new Random().nextInt(validManageCommandEnumValues.size()))).build();
        suggestionRequest = SuggestionRequest.builder().regionObjectId(1405113L).namePart("Земляной Вал").build();
        address = Address.builder().id(UUID.randomUUID()).recordId(1729288L).recordType("ADDR_OBJ").objectId(1405113L).isActive(true).name("Москва").type("г").level(1).prevRecordId(0L).guid(UUID.randomUUID()).isActual(true).build();
        garIdxAddress = GARIdxAddress.builder().id(UUID.randomUUID()).objectId(1418929L).parentObjectId(1405113L).guid(UUID.fromString("0ad30429-7f96-47b8-9c73-63c97a630297")).name("Земляной Вал").type("ул").level(8).fullName("Москва г, Земляной Вал ул").path("1405113.1418929").regionObjectId(1405113L).isActiveAddr(true).isActualAddr(true).isActiveRel(true).build();
    }

    @Test
    @DisplayName("Check ManageCommand Enum have a valid values")
    public void manage_ReturnsTrueIfManageCommandEnumValuesIsValid() {
        List<String> currentManageCommandEnumValues = Arrays.stream(ManageCommand.values()).map(command -> command.toString().toUpperCase()).collect(Collectors.toList());
        assertTrue(currentManageCommandEnumValues.containsAll(validManageCommandEnumValues));
    }

    @Test
    @DisplayName("POST /address_service/manage")
    public void manage_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = post("/address_service/manage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manageRequest));

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errorMessage").hasJsonPath(),
                        jsonPath("$.errorCode").hasJsonPath(),
                        jsonPath("$.executionStart").hasJsonPath(),
                        jsonPath("$.executionEnd").hasJsonPath()
                );
    }

    @Test
    @DisplayName("GET /address_service/regions")
    public void getRegions_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = get("/address_service/regions");
        when(addressService.getRegions()).thenReturn(List.of(address));

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json;charset=utf-8"),
                        jsonPath("$[*].objectId").value(1405113),
                        jsonPath("$[*].name").value("Москва"),
                        jsonPath("$[*].type").value("г"),
                        jsonPath("$[*].nameType").value("Москва г")
                );
    }

    @Test
    @DisplayName("POST /address_service/suggestions")
    public void getSuggestions_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = post("/address_service/suggestions")
                .contentType(MediaType.valueOf("application/json;charset=utf-8"))
                .content(objectMapper.writeValueAsString(suggestionRequest));
        doReturn(List.of(garIdxAddress)).when(addressService).getSuggestions(suggestionRequest.getRegionObjectId(), suggestionRequest.getNamePart());

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json;charset=utf-8"),
                        jsonPath("$[*].objectId").value(1418929),
                        jsonPath("$[*].guid").value("0ad30429-7f96-47b8-9c73-63c97a630297"),
                        jsonPath("$[*].name").value("Земляной Вал"),
                        jsonPath("$[*].type").value("ул"),
                        jsonPath("$[*].fullName").value("Москва г, Земляной Вал ул")
                );
    }
}
