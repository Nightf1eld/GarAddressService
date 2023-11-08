package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Integer recordId;

    @Column(name = "record_type_cd")
    private String recordType;

    @Column(name = "object_id")
    private Integer objectId;

    @Column(name = "object_guid")
    private UUID guid;
}
