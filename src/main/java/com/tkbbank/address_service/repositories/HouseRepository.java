package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, UUID> {

}
