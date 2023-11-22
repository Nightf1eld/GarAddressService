package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.HistoricalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoricalAddressRepository extends JpaRepository<HistoricalAddress, UUID> {
    Optional<HistoricalAddress> getAddressByGuid(UUID guid);
    List<HistoricalAddress> findAllByLevelOrderByName(Integer lvl);
    List<HistoricalAddress> findAllByTypeOrderByName(String type);
}
