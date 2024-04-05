package com.tkbbank.address_service.repositories;

import com.tkbbank.address_service.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

    List<Dictionary> findAllByType(String type);
}
