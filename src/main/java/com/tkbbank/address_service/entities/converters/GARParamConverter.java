package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.HouseParam;
import com.tkbbank.address_service.entities.utils.GARParam;

public class GARParamConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        if (hierarchicalStreamReader.getAttribute("TYPEID").equals("5")) {
            GARParam houseParam = new HouseParam();
            paramMapper(hierarchicalStreamReader, houseParam, "HOUSE_PARAM");
            return houseParam;
        } else {
            return null;
        }
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARParam.class);
    }

    private void paramMapper(HierarchicalStreamReader hierarchicalStreamReader, GARParam param, String recordType) {
        super.objectMapper(hierarchicalStreamReader, param, recordType);
        param.setValue(hierarchicalStreamReader.getAttribute("VALUE"));
        if (checkEmpty(hierarchicalStreamReader, "TYPEID")) {
            param.setTypeId((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("TYPEID")));
        }
    }
}