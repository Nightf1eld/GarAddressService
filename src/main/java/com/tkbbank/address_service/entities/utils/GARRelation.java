package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@MappedSuperclass
@Getter
@Setter
public class GARRelation extends GARObject {

    @Column(name = "par_object_id")
    @XStreamAsAttribute
    @XStreamAlias("PARENTOBJID")
    private Long parentObjectId;

    @Column(name = "path")
    @XStreamAsAttribute
    @XStreamAlias("PATH")
    private String path;

    @Column(name = "type_cd", length = 20)
    private String type;
}
