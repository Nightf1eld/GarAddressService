package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARAddressConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@XStreamConverter(GARAddressConverter.class)
@XStreamAlias("OBJECT")
public class GARAddress extends GARObject {

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "type_cd", length = 50)
    private String type;

    @Column(name = "level_cd")
    private Integer level;

    @Column(name = "prev_record_id")
    private Long prevRecordId;

    @Column(name = "object_guid")
    private UUID guid;

    @Column(name = "actual_flg")
    private Boolean isActual;
}