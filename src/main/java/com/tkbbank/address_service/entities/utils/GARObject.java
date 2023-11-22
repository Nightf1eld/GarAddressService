package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class GARObject {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID id;

    @Column(name = "record_id")
    @XStreamAsAttribute
    @XStreamAlias("ID")
    private Long recordId;

    @Column(name = "record_type_cd", length = 25)
    private String recordType;

    @Column(name = "object_id")
    @XStreamAsAttribute
    @XStreamAlias("OBJECTID")
    private Long objectId;

    @Column(name = "object_guid")
    @XStreamAsAttribute
    @XStreamAlias("OBJECTGUID")
    private UUID guid;

    @Column(name = "actual_flg", length = 1)
    @XStreamAsAttribute
    @XStreamAlias("ISACTUAL")
    private Integer isActual;

    @Column(name = "active_flg", length = 1)
    @XStreamAsAttribute
    @XStreamAlias("ISACTIVE")
    private Integer isActive;
}
