package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.utils.GARAddress;

import java.util.UUID;

public class GARAddressConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARAddress actualAddress = new Address();

        if (hierarchicalStreamReader.getAttribute("ISACTUAL").equals("1") && hierarchicalStreamReader.getAttribute("ISACTIVE").equals("1")) {
            addressMapper(hierarchicalStreamReader, actualAddress, "ADDR_OBJ");
        } else {
            addressMapper(hierarchicalStreamReader, actualAddress, "HIST_ADDR_OBJ");
        }

        return actualAddress;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARAddress.class);
    }


    private void addressMapper(HierarchicalStreamReader hierarchicalStreamReader, GARAddress address, String recordType) {
        super.objectMapper(hierarchicalStreamReader, address, recordType);
        address.setName(hierarchicalStreamReader.getAttribute("NAME"));
        address.setType(hierarchicalStreamReader.getAttribute("TYPENAME"));
        if (checkEmpty(hierarchicalStreamReader, "LEVEL")) {
            address.setLevel((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("LEVEL")));
        }
        if (checkEmpty(hierarchicalStreamReader, "PREVID")) {
            address.setPrevRecordId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("PREVID")));
        }
        if (checkEmpty(hierarchicalStreamReader, "OBJECTGUID")) {
            address.setGuid((UUID) new UUIDConverter().fromString(hierarchicalStreamReader.getAttribute("OBJECTGUID")));
        }
        if (checkEmpty(hierarchicalStreamReader, "ISACTUAL")) {
            address.setIsActual((Boolean) new BooleanConverter("1", "0", true).fromString(hierarchicalStreamReader.getAttribute("ISACTUAL")));
        }
    }
}