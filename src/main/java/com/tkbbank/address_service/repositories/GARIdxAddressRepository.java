package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.UUID;

public interface GARIdxAddressRepository extends JpaRepository<GARIdxAddress, UUID> {

    @Procedure(procedureName = "ADDRESS_SERVICE_PKG.S_ADDR_IDX_INS")
    void idxAddressInsert(String isActive, String isActual, String joinTable, String insertTable, Integer batchSize);

    @Procedure(procedureName = "ADDRESS_SERVICE_PKG.S_ADDR_IDX_UPD")
    void idxAddressUpdate(String updateTable);
}