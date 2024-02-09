package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARHouse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "s_house")
public class House extends GARHouse {

}