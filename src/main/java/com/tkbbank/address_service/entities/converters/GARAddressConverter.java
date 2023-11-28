package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.HistoricalAddress;
import com.tkbbank.address_service.entities.utils.GARAddress;

public class GARAddressConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARAddress actualAddress = new Address();
        GARAddress historicalAddress = new HistoricalAddress();

        if (hierarchicalStreamReader.getAttribute("ISACTUAL").equals("1") && hierarchicalStreamReader.getAttribute("ISACTIVE").equals("1")) {
            addressMapper(hierarchicalStreamReader, actualAddress, "ADDR_OBJ");
            return actualAddress;
        } else {
            addressMapper(hierarchicalStreamReader, historicalAddress, "HIST_ADDR_OBJ");
            return historicalAddress;
        }
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARAddress.class);
    }


    private void addressMapper(HierarchicalStreamReader hierarchicalStreamReader, GARAddress address, String recordType) {
        super.objectMapper(hierarchicalStreamReader, address, recordType);
        address.setName(hierarchicalStreamReader.getAttribute("NAME"));
        address.setType(hierarchicalStreamReader.getAttribute("TYPENAME"));
        if (!checkEmpty(hierarchicalStreamReader, "LEVEL")) {
            address.setLevel((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("LEVEL")));
        }
        if (!checkEmpty(hierarchicalStreamReader, "PREVID")) {
            address.setParentObjectId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("PREVID")));
        }
    }
}