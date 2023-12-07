package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.House;
import com.tkbbank.address_service.entities.HistoricalHouse;
import com.tkbbank.address_service.entities.utils.GARHouse;

import java.util.UUID;

public class GARHouseConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARHouse actualHouse = new House();
        GARHouse historicalHouse = new HistoricalHouse();

        if (hierarchicalStreamReader.getAttribute("ISACTUAL").equals("1") && hierarchicalStreamReader.getAttribute("ISACTIVE").equals("1")) {
            houseMapper(hierarchicalStreamReader, actualHouse, "HOUSE");
            return actualHouse;
        } else {
            houseMapper(hierarchicalStreamReader, historicalHouse, "HIST_HOUSE");
            return historicalHouse;
        }
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARHouse.class);
    }

    private void houseMapper(HierarchicalStreamReader hierarchicalStreamReader, GARHouse house, String recordType) {
        super.objectMapper(hierarchicalStreamReader, house, recordType);
        house.setHouseNumber(hierarchicalStreamReader.getAttribute("HOUSENUM"));
        house.setAdditionalNumber1(hierarchicalStreamReader.getAttribute("ADDNUM1"));
        house.setAdditionalNumber2(hierarchicalStreamReader.getAttribute("ADDNUM2"));
        if (checkEmpty(hierarchicalStreamReader, "ADDTYPE1")) {
            house.setAdditionalType1((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("ADDTYPE1")));
        }
        if (checkEmpty(hierarchicalStreamReader, "ADDTYPE2")) {
            house.setAdditionalType2((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("ADDTYPE2")));
        }
        if (checkEmpty(hierarchicalStreamReader, "HOUSETYPE")) {
            house.setHouseType((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("HOUSETYPE")));
        }
        if (checkEmpty(hierarchicalStreamReader, "OBJECTGUID")) {
            house.setGuid((UUID) new UUIDConverter().fromString(hierarchicalStreamReader.getAttribute("OBJECTGUID")));
        }
        if (checkEmpty(hierarchicalStreamReader, "ISACTUAL")) {
            house.setIsActual((Boolean) new BooleanConverter("1", "0", true).fromString(hierarchicalStreamReader.getAttribute("ISACTUAL")));
        }
    }

}
