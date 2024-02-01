package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.AddressMunRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressMunRelationRepository extends JpaRepository<AddressMunRelation, UUID> {

}