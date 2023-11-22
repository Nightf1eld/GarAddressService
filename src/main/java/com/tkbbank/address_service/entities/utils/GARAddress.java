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
public class GARAddress extends GARObject {

    @Column(name = "name", length = 500)
    @XStreamAsAttribute
    @XStreamAlias("NAME")
    private String name;

    @Column(name = "type_cd", length = 50)
    @XStreamAsAttribute
    @XStreamAlias("TYPENAME")
    private String type;

    @Column(name = "level_cd")
    @XStreamAsAttribute
    @XStreamAlias("LEVEL")
    private Integer level;

    @Column(name = "par_object_id")
    @XStreamAsAttribute
    private Long parentObjectId;
}