package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARHouse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "s_house_hist", indexes = {})
public class HistoricalHouse extends GARHouse {

}