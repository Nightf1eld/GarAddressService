package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tkbbank.address_service.entities.utils.GARObject;

import java.util.UUID;

public class GARObjectConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARObject.class);
    }

    protected void objectMapper(HierarchicalStreamReader hierarchicalStreamReader, GARObject object, String recordType) {
        if (!checkEmpty(hierarchicalStreamReader, "ID")) {
            object.setRecordId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("ID")));
        }
        object.setRecordType(recordType);
        if (!checkEmpty(hierarchicalStreamReader, "OBJECTID")) {
            object.setObjectId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("OBJECTID")));
        }
        if (!checkEmpty(hierarchicalStreamReader, "OBJECTGUID")) {
            object.setGuid((UUID) new UUIDConverter().fromString(hierarchicalStreamReader.getAttribute("OBJECTGUID")));
        }
        if (!checkEmpty(hierarchicalStreamReader, "ISACTUAL")) {
            object.setIsActual((Boolean) new BooleanConverter("1", "0", true).fromString(hierarchicalStreamReader.getAttribute("ISACTUAL")));
        }
        if (!checkEmpty(hierarchicalStreamReader, "ISACTIVE")) {
            object.setIsActive((Boolean) new BooleanConverter("1", "0", true).fromString(hierarchicalStreamReader.getAttribute("ISACTIVE")));
        }
    }

    protected boolean checkEmpty(HierarchicalStreamReader hierarchicalStreamReader, String attribute) {
        boolean check = hierarchicalStreamReader.getAttribute(attribute) == null || hierarchicalStreamReader.getAttribute(attribute).isEmpty() ? true : false;
        return check;
    }
}
