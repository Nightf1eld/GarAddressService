package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.HistoricalHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricalHouseRepository extends JpaRepository<HistoricalHouse, UUID> {

}