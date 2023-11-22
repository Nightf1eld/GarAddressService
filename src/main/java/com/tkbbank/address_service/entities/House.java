package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARHouse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name = "s_house", indexes = {})
@XStreamAlias("HOUSE")
public class House extends GARHouse {

}
