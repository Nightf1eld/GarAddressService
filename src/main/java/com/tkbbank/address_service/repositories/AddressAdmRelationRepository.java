package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.AddressAdmRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AddressAdmRelationRepository extends JpaRepository<AddressAdmRelation, UUID> {
    Optional<AddressAdmRelation> findAddressByPath(String path);
    Set<AddressAdmRelation> findAllByPath(String pathPart); //по части пути
}