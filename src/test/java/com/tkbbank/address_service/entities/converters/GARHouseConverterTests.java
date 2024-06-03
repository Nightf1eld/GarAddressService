package com.tkbbank.address_service.entities.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.HistoricalHouse;
import com.tkbbank.address_service.entities.House;
import com.tkbbank.address_service.entities.utils.GARHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class GARHouseConverterTests {

    private XStream xStream;

    @BeforeEach
    public void setUp() {
        xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(GARHouse.class);
        xStream.ignoreUnknownElements();
    }

    @Test
    @DisplayName("GARHouseConverter (from AS_HOUSES file) return valid House object")
    public void givenHousesXml_whenGARHouseConverter_ReturnValidHouse() {
        // given
        String housesXml = "<HOUSE ID=\"124634294\" OBJECTID=\"159825138\" OBJECTGUID=\"a03a824b-9a69-4dcd-ba4f-fa76245bc7f4\" CHANGEID=\"508539059\" HOUSENUM=\"10\" ADDNUM1=\"1\" ADDNUM2=\"1\" HOUSETYPE=\"2\" ADDTYPE1=\"1\" ADDTYPE2=\"2\" OPERTYPEID=\"10\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-28\" STARTDATE=\"2023-08-28\" ENDDATE=\"2079-06-06\" ISACTUAL=\"1\" ISACTIVE=\"1\"/>";

        // when
        House house = (House) xStream.fromXML(housesXml);

        // then
        assertNotNull(house);
        assertEquals(house.getRecordId(), 124634294);
        assertEquals(house.getRecordType(), "HOUSE");
        assertEquals(house.getObjectId(), 159825138);
        assertEquals(house.getIsActive(), true);
        assertEquals(house.getHouseNumber(), "10");
        assertEquals(house.getAdditionalNumber1(), "1");
        assertEquals(house.getAdditionalNumber2(), "1");
        assertEquals(house.getAdditionalType1(), 1);
        assertEquals(house.getAdditionalType2(), 2);
        assertEquals(house.getHouseType(), 2);
        assertEquals(house.getGuid(), UUID.fromString("a03a824b-9a69-4dcd-ba4f-fa76245bc7f4"));
        assertEquals(house.getIsActual(), true);
    }

    @Test
    @DisplayName("GARHouseConverter (from AS_HOUSES file) return valid HistoricalHouse object")
    public void givenHousesXml_whenGARHouseConverter_ReturnValidHistoricalHouse() {
        // given
        String housesXml = "<HOUSE ID=\"124634294\" OBJECTID=\"159825138\" OBJECTGUID=\"a03a824b-9a69-4dcd-ba4f-fa76245bc7f4\" CHANGEID=\"508539059\" HOUSENUM=\"10\" ADDNUM1=\"1\" ADDNUM2=\"1\" HOUSETYPE=\"2\" ADDTYPE1=\"1\" ADDTYPE2=\"2\" OPERTYPEID=\"10\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-28\" STARTDATE=\"2023-08-28\" ENDDATE=\"2079-06-06\" ISACTUAL=\"0\" ISACTIVE=\"0\"/>";

        // when
        HistoricalHouse historicalHouse = (HistoricalHouse) xStream.fromXML(housesXml);

        // then
        assertNotNull(historicalHouse);
        assertEquals(historicalHouse.getRecordId(), 124634294);
        assertEquals(historicalHouse.getRecordType(), "HIST_HOUSE");
        assertEquals(historicalHouse.getObjectId(), 159825138);
        assertEquals(historicalHouse.getIsActive(), false);
        assertEquals(historicalHouse.getHouseNumber(), "10");
        assertEquals(historicalHouse.getAdditionalNumber1(), "1");
        assertEquals(historicalHouse.getAdditionalNumber2(), "1");
        assertEquals(historicalHouse.getAdditionalType1(), 1);
        assertEquals(historicalHouse.getAdditionalType2(), 2);
        assertEquals(historicalHouse.getHouseType(), 2);
        assertEquals(historicalHouse.getGuid(), UUID.fromString("a03a824b-9a69-4dcd-ba4f-fa76245bc7f4"));
        assertEquals(historicalHouse.getIsActual(), false);
    }
}