package com.tkbbank.address_service.entities;

import com.tkbbank.address_service.entities.utils.GARDictionary;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "s_lst_of_val")
public class Dictionary extends GARDictionary {

}