package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.HistoricalAddressRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricalAddressRelationRepository extends JpaRepository<HistoricalAddressRelation, UUID> {

}