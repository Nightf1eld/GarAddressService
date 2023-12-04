package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARHouseConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@XStreamConverter(GARHouseConverter.class)
@XStreamAlias("HOUSE")
public class GARHouse extends GARObject {

    @Column(name = "house_num", length = 50)
    private String houseNumber;

    @Column(name = "add_num1", length = 50)
    private String additionalNumber1;

    @Column(name = "add_num2", length = 50)
    private String additionalNumber2;

    @Column(name = "add_type1")
    private Integer additionalType1;

    @Column(name = "add_type2")
    private Integer additionalType2;

    @Column(name = "house_type")
    private Integer houseType;

    @Column(name = "object_guid")
    private UUID guid;

    @Column(name = "actual_flg")
    private Boolean isActual;
}
