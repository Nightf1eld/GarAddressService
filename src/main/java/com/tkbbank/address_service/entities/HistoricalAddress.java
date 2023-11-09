package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "s_addr_hist", indexes = {})
public class HistoricalAddress extends GARAddress {

}
