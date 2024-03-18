package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "s_addr_adm_idx")
public class IdxAddressAdm extends GARIdxAddress {

}