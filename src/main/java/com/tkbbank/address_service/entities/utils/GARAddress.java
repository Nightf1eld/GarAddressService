package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class GARAddress extends GARObject {

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "type_cd", length = 50)
    private String type;

    @Column(name = "level_cd")
    private Integer level;
}