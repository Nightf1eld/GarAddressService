package com.tkbbank.address_service.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRequest {
    private Long regionObjectId;
    private String namePart;
}
