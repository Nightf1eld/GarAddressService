package com.tkbbank.address_service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.Dictionary;
import com.tkbbank.address_service.repositories.AddressRepository;
import com.tkbbank.address_service.repositories.DictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private DictionaryRepository dictionaryRepository;

    @InjectMocks
    private AddressService addressService;

    private Dictionary dictionary;
    private Address address;

    @BeforeEach
    public void setUp() {
        dictionary = Dictionary.builder().id(UUID.randomUUID()).type("OBJECTLEVEL").code("1").value("Субъект РФ").description("").level(1).isActive(true).build();
        address = Address.builder().id(UUID.randomUUID()).recordId(1729288L).recordType("ADDR_OBJ").objectId(1405113L).isActive(true).name("Москва").type("г").level(1).prevRecordId(0L).guid(UUID.randomUUID()).isActual(true).build();
    }

    @Test
    @DisplayName("getRegions method (dictionary is not empty)")
    public void givenDictionaryAndAddressList_whenGetRegions_thenReturnAddressList() {
        // given
        given(dictionaryRepository.findAllByType("OBJECTLEVEL")).willReturn(List.of(dictionary));
        given(addressRepository.findAllByLevelAndIsActiveOrderByName(Integer.parseInt(dictionary.getCode()), true)).willReturn(List.of(address));

        // when
        List<Address> addressList = addressService.getRegions();

        // then
        assertEquals(dictionary.getType(), "OBJECTLEVEL");
        assertEquals(dictionary.getCode(), "1");
        assertEquals(dictionary.getValue(), "Субъект РФ");
        assertNotNull(addressList);
        assertEquals(addressList.size(), 1);
    }

    @Test
    @DisplayName("getRegions method (dictionary is empty)")
    public void givenEmptyDictionaryAndAddressList_whenGetRegions_thenReturnAddressList() {
        // given
        given(dictionaryRepository.findAllByType("OBJECTLEVEL")).willReturn(Collections.emptyList());
        given(addressRepository.findAllByLevelAndIsActiveOrderByName(Integer.parseInt(dictionary.getCode()), true)).willReturn(List.of(address));

        // when
        List<Address> addressList = addressService.getRegions();

        // then
        assertNotNull(addressList);
        assertEquals(addressList.size(), 1);
    }
}