package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "s_addr_adm_rel")
public class AddressAdmRelation extends GARRelation {

}