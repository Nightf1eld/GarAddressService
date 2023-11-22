package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name = "s_addr", indexes = {})
@XStreamAlias("OBJECT")
public class Address extends GARAddress {

}
