package com.tkbbank.address_service.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestionRequest {
    private Long regionObjectId;
    private String namePart;
}
