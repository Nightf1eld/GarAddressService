package com.tkbbank.address_service.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ManageResponse {
    private String errorMessage;
    private Integer errorCode;
    private LocalDateTime executionStart;
    private LocalDateTime executionEnd;
}