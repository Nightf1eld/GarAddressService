package com.tkbbank.address_service.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private Long objectId;
    private String name;
    private String type;
    private String nameType;
}