package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GARIdxAddressRepository extends JpaRepository<GARIdxAddress, UUID> {
    @Query(value = "CALL ADDRESS_SERVICE_PKG.S_ADDR_IDX_INS(:isActive, :isActual, :joinTable, :insertTable, :batchSize);", nativeQuery = true)
    void idxAddressInsert(@Param("isActive") String isActive, @Param("isActual") String isActual, @Param("joinTable") String joinTable, @Param("insertTable") String insertTable, @Param("batchSize") Integer batchSize);

    @Query(value = "CALL ADDRESS_SERVICE_PKG.S_ADDR_IDX_UPD(:updateTable, :batchSize);", nativeQuery = true)
    void idxAddressUpdate(@Param("updateTable") String updateTable, @Param("batchSize") Integer batchSize);
}
