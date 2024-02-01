package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.HistoricalAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HistoricalAddressRepository extends JpaRepository<HistoricalAddress, UUID> {

}
