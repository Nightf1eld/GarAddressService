package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.AddressRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AddressRelationRepository extends JpaRepository<AddressRelation, UUID> {
    Optional<AddressRelation> findAddressRelationByGuid(UUID guid);
    List<AddressRelation> findAllByType(String type);
    Optional<AddressRelation> findAddressByPath(String path);
    Set<AddressRelation> findAllByPath(String pathPart); //по части пути
}
