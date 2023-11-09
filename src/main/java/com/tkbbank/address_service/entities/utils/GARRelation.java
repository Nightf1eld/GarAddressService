package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class GARRelation extends GARObject {

    @Column(name = "par_object_id")
    private Long parentObjectId;

    @Column(name = "path")
    private String path;

    @Column(name = "type_cd", length = 20)
    private String type;
}
