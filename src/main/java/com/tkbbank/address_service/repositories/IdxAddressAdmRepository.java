package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.IdxAddressAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IdxAddressAdmRepository extends JpaRepository<IdxAddressAdm, UUID> {

    List<IdxAddressAdm> findAllByRegionObjectIdAndNameStartsWithIgnoreCase(Long regionObjectId, String namePart);
}
