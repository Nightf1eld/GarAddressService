package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class GARRelation extends GARObject {

    @Column(name = "par_object_id")
    private Integer parentObjectId;

    @Column(name = "type_cd", length = 50)
    private String type;
}
