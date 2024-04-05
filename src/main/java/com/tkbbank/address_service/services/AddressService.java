package com.tkbbank.address_service.services;

import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.Dictionary;
import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import com.tkbbank.address_service.repositories.AddressRepository;
import com.tkbbank.address_service.repositories.DictionaryRepository;
import com.tkbbank.address_service.repositories.IdxAddressAdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final IdxAddressAdmRepository idxAddressAdmRepository;
    private final DictionaryRepository dictionaryRepository;

    public List<Address> getRegions() {
        Dictionary subject = dictionaryRepository.findAllByType("OBJECTLEVEL").stream().filter(dictionary -> dictionary.getValue().equals("Субъект РФ") && dictionary.getIsActive()).findAny().orElse(null);
        return addressRepository.findAllByLevelAndIsActiveOrderByName(Integer.parseInt(subject.getCode()), true);
    }

    public List<? extends GARIdxAddress> getSuggestions(Long regionObjectId, String namePart) {
        return idxAddressAdmRepository.findAllByRegionObjectIdAndNameStartsWithIgnoreCase(regionObjectId, namePart);
    }
}