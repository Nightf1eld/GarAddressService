package com.tkbbank.address_service.entities.utils;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GARIdxAddress {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID id;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "par_object_id")
    private Long parentObjectId;

    @Column(name = "object_guid")
    private UUID guid;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "type_cd", length = 50)
    private String type;

    @Column(name = "level_cd")
    private Integer level;

    @FullTextField(name = "edgeNGramMin3Max30_fullName", analyzer = "edgeNGramMin3Max30_analyzer")
    @Column(name = "full_name", length = 2000)
    private String fullName;

    @Column(name = "path")
    private String path;

    @GenericField
    @Column(name = "region_object_id")
    private Long regionObjectId;

    @Column(name = "addr_active_flg")
    private Boolean isActiveAddr;

    @Column(name = "addr_actual_flg")
    private Boolean isActualAddr;

    @Column(name = "rel_active_flg")
    private Boolean isActiveRel;
}
