package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.AddressRelation;
import com.tkbbank.address_service.entities.HistoricalAddressRelation;
import com.tkbbank.address_service.entities.utils.GARRelation;

public class GARRelationConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARRelation actualAddressRelation = new AddressRelation();
        GARRelation historicalAddressRelation = new HistoricalAddressRelation();

        if (hierarchicalStreamReader.getAttribute("ISACTIVE").equals("1")) {
            if (checkMunicipalType(hierarchicalStreamReader)) {
                relationMapper(hierarchicalStreamReader, actualAddressRelation, "ADDR_REL", "MUN");
            } else {
                relationMapper(hierarchicalStreamReader, actualAddressRelation, "ADDR_REL", "ADM");
            }
            return actualAddressRelation;
        } else {
            if (checkMunicipalType(hierarchicalStreamReader)) {
                relationMapper(hierarchicalStreamReader, historicalAddressRelation, "HIST_ADD_REL", "MUN");
            } else {
                relationMapper(hierarchicalStreamReader, historicalAddressRelation, "HIST_ADD_REL", "ADM");
            }
            return historicalAddressRelation;
        }
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARRelation.class);
    }

    private void relationMapper(HierarchicalStreamReader hierarchicalStreamReader, GARRelation relation, String recordType, String divisionType) {
        super.objectMapper(hierarchicalStreamReader, relation, recordType);
        if (!checkEmpty(hierarchicalStreamReader, "PARENTOBJID")) {
            relation.setParentObjectId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("PARENTOBJID")));
        }
        relation.setPath(hierarchicalStreamReader.getAttribute("PATH"));
        relation.setType(divisionType);
    }

    private boolean checkMunicipalType(HierarchicalStreamReader hierarchicalStreamReader) {
        boolean check = hierarchicalStreamReader.getAttribute("OKTMO") != null ? true : false;
        return check;
    }
}
