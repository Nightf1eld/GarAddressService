package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findAllByLevelAndIsActiveOrderByName(Integer level, Boolean isActive);
}
