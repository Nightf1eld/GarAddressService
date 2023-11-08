package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARAddress;
import jakarta.persistence.*;

@Entity
@Table(name = "s_addr", indexes = {})
public class Address extends GARAddress {

}
