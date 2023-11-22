package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name = "s_addr_rel", indexes = {})
@XStreamAlias("ITEM")
public class AddressRelation extends GARRelation {

}