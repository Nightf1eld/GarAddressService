package com.tkbbank.address_service.services;

import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.Dictionary;
import com.tkbbank.address_service.entities.IdxAddressAdm;
import com.tkbbank.address_service.entities.utils.GARIdxAddress;
import com.tkbbank.address_service.repositories.AddressRepository;
import com.tkbbank.address_service.repositories.DictionaryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final DictionaryRepository dictionaryRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Address> getRegions() {
        Dictionary subject = dictionaryRepository.findAllByType("OBJECTLEVEL").stream().filter(dictionary -> dictionary.getValue().equals("Субъект РФ") && dictionary.getIsActive()).findAny().orElse(null);
        return addressRepository.findAllByLevelAndIsActiveOrderByName(Integer.parseInt(subject.getCode()), true);
    }

    public List<? extends GARIdxAddress> getSuggestions(Long regionObjectId, String namePart) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SearchSession searchSession = Search.session(entityManager);

        List<? extends GARIdxAddress> suggestions = searchSession.search(IdxAddressAdm.class)
                .where(f -> f.and(
                        f.match()
                                .field("regionObjectId")
                                .matching(regionObjectId),
                        f.match()
                                .field("edgeNGramMin3Max30_fullName")
                                .matching(namePart)
                ))
                .fetchHits(10);

        return suggestions;
    }
}