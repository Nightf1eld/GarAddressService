package com.tkbbank.address_service.entities.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.utils.GARAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class GARAddressConverterTests {

    private XStream xStream;

    @BeforeEach
    public void setUp() {
        xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(GARAddress.class);
        xStream.ignoreUnknownElements();
    }

    @Test
    @DisplayName("GARAddressConverter (from AS_ADDR_OBJ file) return valid actual Address object")
    public void givenAddrObjXml_whenGARAddressConverter_ReturnValidAddress() {
        // given
        String addrObjXml = "<OBJECT ID=\"52067143\" OBJECTID=\"159831381\" OBJECTGUID=\"b271bc48-f831-4784-87c2-eeaec3917752\" CHANGEID=\"508559058\" NAME=\"Изумрудная\" TYPENAME=\"ул.\" LEVEL=\"8\" OPERTYPEID=\"10\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-29\" STARTDATE=\"2023-08-29\" ENDDATE=\"2079-06-06\" ISACTUAL=\"1\" ISACTIVE=\"1\"/>";

        // when
        Address actualAddress = (Address) xStream.fromXML(addrObjXml);

        // then
        assertNotNull(actualAddress);
        assertEquals(actualAddress.getRecordId(), 52067143);
        assertEquals(actualAddress.getRecordType(), "ADDR_OBJ");
        assertEquals(actualAddress.getObjectId(), 159831381);
        assertEquals(actualAddress.getIsActive(), true);
        assertEquals(actualAddress.getName(), "Изумрудная");
        assertEquals(actualAddress.getType(), "ул.");
        assertEquals(actualAddress.getLevel(), 8);
        assertEquals(actualAddress.getPrevRecordId(), 0);
        assertEquals(actualAddress.getGuid(), UUID.fromString("b271bc48-f831-4784-87c2-eeaec3917752"));
        assertEquals(actualAddress.getIsActual(), true);
    }

    @Test
    @DisplayName("GARAddressConverter (from AS_ADDR_OBJ file) return valid historical Address object")
    public void givenAddrObjXml_whenGARAddressConverter_ReturnValidHistoricalAddress() {
        // given
        String addrObjXml = "<OBJECT ID=\"52067143\" OBJECTID=\"159831381\" OBJECTGUID=\"b271bc48-f831-4784-87c2-eeaec3917752\" CHANGEID=\"508559058\" NAME=\"Изумрудная\" TYPENAME=\"ул.\" LEVEL=\"8\" OPERTYPEID=\"10\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-29\" STARTDATE=\"2023-08-29\" ENDDATE=\"2079-06-06\" ISACTUAL=\"0\" ISACTIVE=\"0\"/>";

        // when
        Address historicalAddress = (Address) xStream.fromXML(addrObjXml);

        // then
        assertNotNull(historicalAddress);
        assertEquals(historicalAddress.getRecordId(), 52067143);
        assertEquals(historicalAddress.getRecordType(), "HIST_ADDR_OBJ");
        assertEquals(historicalAddress.getObjectId(), 159831381);
        assertEquals(historicalAddress.getIsActive(), false);
        assertEquals(historicalAddress.getName(), "Изумрудная");
        assertEquals(historicalAddress.getType(), "ул.");
        assertEquals(historicalAddress.getLevel(), 8);
        assertEquals(historicalAddress.getPrevRecordId(), 0);
        assertEquals(historicalAddress.getGuid(), UUID.fromString("b271bc48-f831-4784-87c2-eeaec3917752"));
        assertEquals(historicalAddress.getIsActual(), false);
    }
}
