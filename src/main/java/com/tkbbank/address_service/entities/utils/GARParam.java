package com.tkbbank.address_service.entities.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARParamConverter;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@XStreamConverter(GARParamConverter.class)
@XStreamAlias("PARAM")
public class GARParam extends GARObject {

    @Column(name = "value")
    private String value;

    @Column(name = "type_id")
    private Integer typeId;
}