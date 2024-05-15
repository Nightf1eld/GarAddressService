package com.tkbbank.address_service.entities.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.AddressAdmRelation;
import com.tkbbank.address_service.entities.AddressMunRelation;
import com.tkbbank.address_service.entities.HistoricalAddressAdmRelation;
import com.tkbbank.address_service.entities.HistoricalAddressMunRelation;
import com.tkbbank.address_service.entities.utils.GARRelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARRelationConverterTests {

    private XStream xStream;

    @BeforeEach
    public void setUp() {
        xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(GARRelation.class);
        xStream.ignoreUnknownElements();
    }

    @Test
    @DisplayName("GARRelationConverter (from AS_ADM_HIERARCHY file) return valid AddressAdmRelation object")
    public void givenAdmHierarchyXml_whenGARRelationConverter_ReturnValidAddressAdmRelation() {
        // given
        String admHierarchyXml = "<ITEM ID=\"229315531\" OBJECTID=\"159824655\" PARENTOBJID=\"47218564\" CHANGEID=\"508537391\" REGIONCODE=\"50\" AREACODE=\"0\" CITYCODE=\"69\" PLACECODE=\"357\" PLANCODE=\"0\" STREETCODE=\"0\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-28\" STARTDATE=\"2023-08-28\" ENDDATE=\"2079-06-06\" ISACTIVE=\"1\" PATH=\"807356.862378.874462.47218564.159824655\"/>";

        // when
        AddressAdmRelation addressAdmRelation = (AddressAdmRelation) xStream.fromXML(admHierarchyXml);

        // then
        assertNotNull(addressAdmRelation);
        assertEquals(addressAdmRelation.getRecordId(), 229315531);
        assertEquals(addressAdmRelation.getRecordType(), "ADDR_ADM_REL");
        assertEquals(addressAdmRelation.getObjectId(), 159824655);
        assertEquals(addressAdmRelation.getIsActive(), true);
        assertEquals(addressAdmRelation.getParentObjectId(), 47218564);
        assertEquals(addressAdmRelation.getPath(), "807356.862378.874462.47218564.159824655");
    }

    @Test
    @DisplayName("GARRelationConverter (from AS_ADM_HIERARCHY file) return valid HistoricalAddressAdmRelation object")
    public void givenAdmHierarchyXml_whenGARRelationConverter_ReturnValidHistoricalAddressAdmRelation() {
        // given
        String admHierarchyXml = "<ITEM ID=\"229315531\" OBJECTID=\"159824655\" PARENTOBJID=\"47218564\" CHANGEID=\"508537391\" REGIONCODE=\"50\" AREACODE=\"0\" CITYCODE=\"69\" PLACECODE=\"357\" PLANCODE=\"0\" STREETCODE=\"0\" PREVID=\"0\" NEXTID=\"0\" UPDATEDATE=\"2023-08-28\" STARTDATE=\"2023-08-28\" ENDDATE=\"2079-06-06\" ISACTIVE=\"0\" PATH=\"807356.862378.874462.47218564.159824655\"/>";

        // when
        HistoricalAddressAdmRelation historicalAddressAdmRelation = (HistoricalAddressAdmRelation) xStream.fromXML(admHierarchyXml);

        // then
        assertNotNull(historicalAddressAdmRelation);
        assertEquals(historicalAddressAdmRelation.getRecordId(), 229315531);
        assertEquals(historicalAddressAdmRelation.getRecordType(), "HIST_ADDR_ADM_REL");
        assertEquals(historicalAddressAdmRelation.getObjectId(), 159824655);
        assertEquals(historicalAddressAdmRelation.getIsActive(), false);
        assertEquals(historicalAddressAdmRelation.getParentObjectId(), 47218564);
        assertEquals(historicalAddressAdmRelation.getPath(), "807356.862378.874462.47218564.159824655");
    }

    @Test
    @DisplayName("GARRelationConverter (from AS_MUN_HIERARCHY file) return valid AddressMunRelation object")
    public void givenMunHierarchyXml_whenGARRelationConverter_ReturnValidAddressMunRelation() {
        // given
        String munHierarchyXml = "<ITEM ID=\"23449077\" OBJECTID=\"38366294\" PARENTOBJID=\"809923\" CHANGEID=\"58171270\" OKTMO=\"46766000202\" PREVID=\"0\" NEXTID=\"228281111\" UPDATEDATE=\"2023-09-01\" STARTDATE=\"2018-05-18\" ENDDATE=\"2023-09-01\" ISACTIVE=\"1\" PATH=\"807356.95244371.809923.38366294\"/>";

        // when
        AddressMunRelation addressMunRelation = (AddressMunRelation) xStream.fromXML(munHierarchyXml);

        // then
        assertNotNull(addressMunRelation);
        assertEquals(addressMunRelation.getRecordId(), 23449077);
        assertEquals(addressMunRelation.getRecordType(), "ADDR_MUN_REL");
        assertEquals(addressMunRelation.getObjectId(), 38366294);
        assertEquals(addressMunRelation.getIsActive(), true);
        assertEquals(addressMunRelation.getParentObjectId(), 809923);
        assertEquals(addressMunRelation.getPath(), "807356.95244371.809923.38366294");
    }

    @Test
    @DisplayName("GARRelationConverter (from AS_MUN_HIERARCHY file) return valid HistoricalAddressMunRelation object")
    public void givenMunHierarchyXml_whenGARRelationConverter_ReturnValidHistoricalAddressMunRelation() {
        // given
        String munHierarchyXml = "<ITEM ID=\"23449077\" OBJECTID=\"38366294\" PARENTOBJID=\"809923\" CHANGEID=\"58171270\" OKTMO=\"46766000202\" PREVID=\"0\" NEXTID=\"228281111\" UPDATEDATE=\"2023-09-01\" STARTDATE=\"2018-05-18\" ENDDATE=\"2023-09-01\" ISACTIVE=\"0\" PATH=\"807356.95244371.809923.38366294\"/>";

        // when
        HistoricalAddressMunRelation historicalAddressMunRelation = (HistoricalAddressMunRelation) xStream.fromXML(munHierarchyXml);

        // then
        assertNotNull(historicalAddressMunRelation);
        assertEquals(historicalAddressMunRelation.getRecordId(), 23449077);
        assertEquals(historicalAddressMunRelation.getRecordType(), "HIST_ADDR_MUN_REL");
        assertEquals(historicalAddressMunRelation.getObjectId(), 38366294);
        assertEquals(historicalAddressMunRelation.getIsActive(), false);
        assertEquals(historicalAddressMunRelation.getParentObjectId(), 809923);
        assertEquals(historicalAddressMunRelation.getPath(), "807356.95244371.809923.38366294");
    }
}