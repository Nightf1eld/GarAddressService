package com.tkbbank.address_service.entities.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.HouseParam;
import com.tkbbank.address_service.entities.utils.GARParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARParamConverterTests {

    private XStream xStream;

    @BeforeEach
    public void setUp() {
        xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(GARParam.class);
        xStream.ignoreUnknownElements();
    }

    @Test
    @DisplayName("GARParamConverter (from AS_HOUSES_PARAMS file) return valid HouseParam object (TYPEID = 5)")
    public void givenHousesParamsXml_whenGARParamConverter_ReturnValidHouseParam() {
        // given
        String housesParamsXml = "<PARAM ID=\"1401081022\" OBJECTID=\"159574304\" CHANGEID=\"508906590\" CHANGEIDEND=\"0\" TYPEID=\"5\" VALUE=\"141862\" UPDATEDATE=\"2023-08-30\" STARTDATE=\"2023-08-30\" ENDDATE=\"2079-06-06\"/>";

        // when
        HouseParam houseParam = (HouseParam) xStream.fromXML(housesParamsXml);

        // then
        assertNotNull(houseParam);
        assertEquals(houseParam.getRecordId(), 1401081022);
        assertEquals(houseParam.getRecordType(), "HOUSE_PARAM");
        assertEquals(houseParam.getObjectId(), 159574304);
        assertNull(houseParam.getIsActive());
        assertEquals(houseParam.getValue(), "141862");
        assertEquals(houseParam.getTypeId(),5);
    }

    @Test
    @DisplayName("GARParamConverter (from AS_HOUSES_PARAMS file) return valid HouseParam object (all other TYPEID)")
    public void givenHousesParamsXml_whenGARParamConverter_ReturnNullHouseParam() {
        // given
        String housesParamsXml = "<PARAM ID=\"1401081022\" OBJECTID=\"159574304\" CHANGEID=\"508906590\" CHANGEIDEND=\"0\" TYPEID=\"15\" VALUE=\"141862\" UPDATEDATE=\"2023-08-30\" STARTDATE=\"2023-08-30\" ENDDATE=\"2079-06-06\"/>";

        // when
        HouseParam houseParam = (HouseParam) xStream.fromXML(housesParamsXml);

        // then
        assertNull(houseParam);
    }
}