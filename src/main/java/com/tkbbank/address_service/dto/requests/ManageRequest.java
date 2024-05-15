package com.tkbbank.address_service.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageRequest {
    private String command;
}