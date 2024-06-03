package com.tkbbank.address_service.entities.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.Dictionary;
import com.tkbbank.address_service.entities.utils.GARDictionary;
import com.tkbbank.address_service.enums.EntitiesFileMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARDictionaryConverterTests {

    private XStream xStream;

    @BeforeEach
    public void setUp() {
        xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(GARDictionary.class);
        xStream.ignoreUnknownElements();
    }

    @Test
    @DisplayName("GARDictionaryConverter (from AS_OBJECT_LEVELS file) return valid Dictionary object")
    public void givenObjectLevelsXml_whenGARDictionaryConverter_ReturnValidDictionary() {
        // given
        String objectLevelsXml = "<OBJECTLEVEL ISACTIVE=\"true\" UPDATEDATE=\"1900-01-01\" ENDDATE=\"2079-06-06\" STARTDATE=\"1900-01-01\" NAME=\"Субъект РФ\" LEVEL=\"1\"/>";

        // when
        xStream.alias(EntitiesFileMatcher.AS_OBJECT_LEVELS.getAliasMatcher(), GARDictionary.class);
        Dictionary dictionary = (Dictionary) xStream.fromXML(objectLevelsXml);

        // then
        assertNotNull(dictionary);
        assertEquals(dictionary.getType(), "OBJECTLEVEL");
        assertEquals(dictionary.getCode(), "1");
        assertEquals(dictionary.getValue(), "Субъект РФ");
        assertNull(dictionary.getDescription());
        assertEquals(dictionary.getLevel(), 1);
        assertEquals(dictionary.getIsActive(), true);
    }

    @Test
    @DisplayName("GARDictionaryConverter (from AS_ADDHOUSE_TYPES file) return valid Dictionary object")
    public void givenAddHouseTypesXml_whenGARDictionaryConverter_ReturnValidDictionary() {
        // given
        String addHouseTypesXml = "<HOUSETYPE ENDDATE=\"2079-06-06\" STARTDATE=\"2015-12-25\" UPDATEDATE=\"2015-12-25\" ISACTIVE=\"true\" DESC=\"Корпус\" SHORTNAME=\"к.\" NAME=\"Корпус\" ID=\"1\"/>";

        // when
        xStream.alias(EntitiesFileMatcher.AS_ADDHOUSE_TYPES.getAliasMatcher(), GARDictionary.class);
        Dictionary dictionary = (Dictionary) xStream.fromXML(addHouseTypesXml);

        // then
        assertNotNull(dictionary);
        assertEquals(dictionary.getType(), "HOUSETYPE");
        assertEquals(dictionary.getCode(), "к.");
        assertEquals(dictionary.getValue(), "Корпус");
        assertEquals(dictionary.getDescription(), "Корпус");
        assertNull(dictionary.getLevel());
        assertEquals(dictionary.getIsActive(), true);
    }

    @Test
    @DisplayName("GARDictionaryConverter (from AS_ADDR_OBJ_TYPES file) return valid Dictionary object")
    public void givenAddrObjTypesXml_whenGARDictionaryConverter_ReturnValidDictionary() {
        // given
        String addrObjTypesXml = "<ADDRESSOBJECTTYPE ISACTIVE=\"true\" ENDDATE=\"2015-11-05\" STARTDATE=\"1900-01-01\" UPDATEDATE=\"1900-01-01\" DESC=\"Область\" SHORTNAME=\"обл\" NAME=\"Область\" LEVEL=\"1\" ID=\"10\"/>";

        // when
        xStream.alias(EntitiesFileMatcher.AS_ADDR_OBJ_TYPES.getAliasMatcher(), GARDictionary.class);
        Dictionary dictionary = (Dictionary) xStream.fromXML(addrObjTypesXml);

        // then
        assertNotNull(dictionary);
        assertEquals(dictionary.getType(), "ADDRESSOBJECTTYPE");
        assertEquals(dictionary.getCode(), "обл");
        assertEquals(dictionary.getValue(), "Область");
        assertEquals(dictionary.getDescription(), "Область");
        assertEquals(dictionary.getLevel(), 1);
        assertEquals(dictionary.getIsActive(), true);
    }

    @Test
    @DisplayName("GARDictionaryConverter (from AS_HOUSE_TYPES file) return valid Dictionary object")
    public void givenHouseTypesXml_whenGARDictionaryConverter_ReturnValidDictionary() {
        // given
        String houseTypesXml = "<HOUSETYPE ENDDATE=\"2079-06-06\" STARTDATE=\"1900-01-01\" UPDATEDATE=\"1900-01-01\" ISACTIVE=\"true\" DESC=\"Дом\" SHORTNAME=\"д.\" NAME=\"Дом\" ID=\"2\"/>";

        // when
        xStream.alias(EntitiesFileMatcher.AS_HOUSE_TYPES.getAliasMatcher(), GARDictionary.class);
        Dictionary dictionary = (Dictionary) xStream.fromXML(houseTypesXml);

        // then
        assertNotNull(dictionary);
        assertEquals(dictionary.getType(), "HOUSETYPE");
        assertEquals(dictionary.getCode(), "д.");
        assertEquals(dictionary.getValue(), "Дом");
        assertEquals(dictionary.getDescription(), "Дом");
        assertNull(dictionary.getLevel());
        assertEquals(dictionary.getIsActive(), true);
    }

    @Test
    @DisplayName("GARDictionaryConverter (from AS_PARAM_TYPES file) return valid Dictionary object")
    public void givenParamTypesXml_whenGARDictionaryConverter_ReturnValidDictionary() {
        // given
        String paramTypesXml = "<PARAMTYPE ENDDATE=\"2079-06-06\" STARTDATE=\"2011-11-01\" UPDATEDATE=\"2018-06-15\" ISACTIVE=\"true\" CODE=\"PostIndex\" DESC=\"Информация о почтовом индексе\" NAME=\"Почтовый индекс\" ID=\"5\"/>";

        // when
        xStream.alias(EntitiesFileMatcher.AS_PARAM_TYPES.getAliasMatcher(), GARDictionary.class);
        Dictionary dictionary = (Dictionary) xStream.fromXML(paramTypesXml);

        // then
        assertNotNull(dictionary);
        assertEquals(dictionary.getType(), "PARAMTYPE");
        assertEquals(dictionary.getCode(), "PostIndex");
        assertEquals(dictionary.getValue(), "Почтовый индекс");
        assertEquals(dictionary.getDescription(), "Информация о почтовом индексе");
        assertNull(dictionary.getLevel());
        assertEquals(dictionary.getIsActive(), true);
    }
}
