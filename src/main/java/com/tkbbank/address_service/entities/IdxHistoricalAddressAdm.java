package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
@Table(name = "s_addr_adm_idx_hist")
public class IdxHistoricalAddressAdm extends GARIdxAddress {

}