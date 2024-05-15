package com.tkbbank.address_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkbbank.address_service.dto.requests.ManageRequest;
import com.tkbbank.address_service.dto.requests.SuggestionRequest;
import com.tkbbank.address_service.services.LoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AddressControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LoaderService loaderService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ManageRequest manageRequestLoadCase;
    private ManageRequest manageRequestIndexEntitiesCase;
    private SuggestionRequest suggestionRequest;

    @BeforeEach
    public void setUp() {
        loaderService.indexEntities(false);
        webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build();
        manageRequestLoadCase = ManageRequest.builder().command("LOAD").build();
        manageRequestIndexEntitiesCase = ManageRequest.builder().command("INDEX_ENTITIES").build();
        suggestionRequest = SuggestionRequest.builder().regionObjectId(1405113L).namePart("Земляной Вал").build();
    }

    @Test
    public void manage_LoadCase_IntegrationTest() throws JsonProcessingException {
        webTestClient.post()
                .uri("address_service/manage")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(manageRequestLoadCase)))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(4)
                .jsonPath("$.errorMessage").hasJsonPath()
                .jsonPath("$.errorCode").hasJsonPath()
                .jsonPath("$.executionStart").hasJsonPath()
                .jsonPath("$.executionEnd").hasJsonPath();
    }

    @Test
    public void manage_IndexEntitiesCase_IntegrationTest() throws JsonProcessingException {
        webTestClient.post()
                .uri("address_service/manage")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(manageRequestIndexEntitiesCase)))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(4)
                .jsonPath("$.errorMessage").hasJsonPath()
                .jsonPath("$.errorCode").hasJsonPath()
                .jsonPath("$.executionStart").hasJsonPath()
                .jsonPath("$.executionEnd").hasJsonPath();
    }

    @Test
    public void getRegions_IntegrationTest() {
        webTestClient.get()
                .uri("address_service/regions")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=utf-8")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$[*].objectId").hasJsonPath()
                .jsonPath("$[*].name").hasJsonPath()
                .jsonPath("$[*].type").hasJsonPath()
                .jsonPath("$[*].nameType").hasJsonPath();
    }

    @Test
    public void getSuggestions_IntegrationTest() throws JsonProcessingException {
        webTestClient.post()
                .uri("address_service/suggestions")
                .header("Content-Type", "application/json;charset=utf-8")
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(suggestionRequest)))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=utf-8")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].objectId").isEqualTo(1418929)
                .jsonPath("$[0].guid").isEqualTo("873ab6c6-8b52-402a-a678-3d7aa9899048")
                .jsonPath("$[0].name").isEqualTo("Земляной Вал")
                .jsonPath("$[0].type").isEqualTo("ул")
                .jsonPath("$[0].fullName").isEqualTo("Москва г, Земляной Вал ул");
    }
}