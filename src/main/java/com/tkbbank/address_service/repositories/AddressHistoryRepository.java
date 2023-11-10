package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.HistoricalAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressHistoryRepository extends JpaRepository<HistoricalAddress, UUID> {
    Optional<HistoricalAddress> getAddressByGUID(String guid);
    List<HistoricalAddress> findAllByLevelOrderByName(Integer lvl);
    List<HistoricalAddress> findAllByTypeAndSortedByName(String type);
}
