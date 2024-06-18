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

import java.util.*;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final DictionaryRepository dictionaryRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Address> getRegions() {
        String defaultAddressLevel = "1";
        Dictionary subject = dictionaryRepository.findAllByType("OBJECTLEVEL").stream().filter(dictionary -> dictionary.getValue().equals("Субъект РФ") && dictionary.getIsActive()).findAny().orElse(null);
        return addressRepository.findAllByLevelAndIsActiveOrderByName(Integer.parseInt(subject != null ? subject.getCode() : defaultAddressLevel), true);
    }

    public List<? extends GARIdxAddress> getSuggestions(Long regionObjectId, String namePart) {

        List<? extends GARIdxAddress> suggestions;

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            SearchSession searchSession = Search.session(entityManager);

            suggestions = searchSession.search(IdxAddressAdm.class)
                    .where(f -> f.bool()
                            .must(f.match()
                                    .field("regionObjectId")
                                    .matching(regionObjectId))
                            .must(f.match()
                                    .field("whitespace_edgeNGramMin3Max30_fullName")
                                    .matching(namePart))
                            .should(f.match()
                                    .field("whitespace_fullName")
                                    .matching(namePart))
                            .should(f.bool()
                                    .should(f.match()
                                            .field("type")
                                            .matching("ул"))
                                    .should(f.bool()
                                            .should(f.match()
                                                    .field("hierarchyLevel")
                                                    .matching(2)
                                                    .boost(6.0f))
                                            .should(f.match()
                                                    .field("hierarchyLevel")
                                                    .matching(3)
                                                    .boost(5.0f))
                                            .should(f.match()
                                                    .field("hierarchyLevel")
                                                    .matching(4)
                                                    .boost(4.0f))
                                            .should(f.match()
                                                    .field("hierarchyLevel")
                                                    .matching(5)
                                                    .boost(3.0f))
                                            .should(f.match()
                                                    .field("hierarchyLevel")
                                                    .matching(6)
                                                    .boost(2.0f))))
                    )
                    .fetchHits(20);
        }

        return suggestions;
    }
}