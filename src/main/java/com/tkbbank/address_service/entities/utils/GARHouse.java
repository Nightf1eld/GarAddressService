package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class GARHouse extends GARObject {

    @Column(name = "house_num", length = 20)
    private String houseNumber;

    @Column(name = "add_num1", length = 20)
    private String additionalNumber1;

    @Column(name = "add_num2", length = 20)
    private String additionalNumber2;

    @Column(name = "add_type1")
    private Integer additionalType1;

    @Column(name = "add_type2")
    private Integer additionalType2;

    @Column(name = "house_type")
    private Integer houseType;
}
