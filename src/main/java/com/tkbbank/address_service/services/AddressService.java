package com.tkbbank.address_service.services;

import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import com.tkbbank.address_service.repositories.AddressRepository;
import com.tkbbank.address_service.repositories.IdxAddressAdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final IdxAddressAdmRepository idxAddressAdmRepository;

    public List<Address> getRegions() {
        return addressRepository.findAllByLevelAndIsActiveOrderByName(1, true);
    }

    public List<? extends GARIdxAddress> getSuggestions(Long regionObjectId, String namePart) {
        return idxAddressAdmRepository.findAllByRegionObjectIdAndNameStartsWithIgnoreCase(regionObjectId, namePart);
    }
}