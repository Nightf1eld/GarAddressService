package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.utils.GARObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GARObjectRepository extends JpaRepository<GARObject, UUID> {

}
