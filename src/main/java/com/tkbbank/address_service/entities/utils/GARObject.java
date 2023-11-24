package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
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
    private Long recordId;

    @Column(name = "record_type_cd", length = 25)
    private String recordType;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_guid")
    private UUID guid;

    @Column(name = "actual_flg")
    private Boolean isActual;

    @Column(name = "active_flg")
    private Boolean isActive;
}
