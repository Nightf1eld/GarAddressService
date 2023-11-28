package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.HistoricalAddressRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricalAddressRelationRepository extends JpaRepository<HistoricalAddressRelation, UUID> {

}