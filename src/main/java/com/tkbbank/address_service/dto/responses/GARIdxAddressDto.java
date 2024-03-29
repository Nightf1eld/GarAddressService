package com.tkbbank.address_service.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GARIdxAddressDto {
    private Long objectId;
    private UUID guid;
    private String name;
    private String type;
    private String fullName;
}
