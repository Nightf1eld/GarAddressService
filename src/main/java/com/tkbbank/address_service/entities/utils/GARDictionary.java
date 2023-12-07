package com.tkbbank.address_service.entities.utils;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.tkbbank.address_service.entities.converters.GARDictionaryConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@XStreamConverter(GARDictionaryConverter.class)
@Table(name = "s_lst_of_val", indexes = {})
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

    @Column(name = "active_flg")
    private Boolean isActive;
}
