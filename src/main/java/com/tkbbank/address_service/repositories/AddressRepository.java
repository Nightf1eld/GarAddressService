package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> getAddressByGuid(UUID guid);
    List<Address> findAllByLevelOrderByName(Integer lvl);
    List<Address> findAllByTypeOrderByName(String type);
}
