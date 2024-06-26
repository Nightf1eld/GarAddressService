package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.*;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARDictionaryConverter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@XStreamConverter(GARDictionaryConverter.class)
public class GARDictionary {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID id;

    @Column(name = "type_cd", length = 50)
    private String type;

    @Column(name = "lic", length = 50)
    private String code;

    @Column(name = "val", length = 250)
    private String value;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "level_cd", length = 10)
    private Integer level;

    @Column(name = "active_flg")
    private Boolean isActive;
}
