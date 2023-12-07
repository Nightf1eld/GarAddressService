package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARRelationConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@MappedSuperclass
@Getter
@Setter
@XStreamConverter(GARRelationConverter.class)
@XStreamAlias("ITEM")
public class GARRelation extends GARObject {

    @Column(name = "par_object_id")
    private Long parentObjectId;

    @Column(name = "path")
    private String path;
}
