package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.tkbbank.address_service.entities.utils.GARRelation;
import com.tkbbank.address_service.entities.AddressAdmRelation;
import com.tkbbank.address_service.entities.AddressMunRelation;
import com.tkbbank.address_service.entities.HistoricalAddressAdmRelation;
import com.tkbbank.address_service.entities.HistoricalAddressMunRelation;

public class GARRelationConverter extends GARObjectConverter {

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARRelation actualAddressAdmRelation = new AddressAdmRelation();
        GARRelation actualAddressMunRelation = new AddressMunRelation();
        GARRelation historicalAddressAdmRelation = new HistoricalAddressAdmRelation();
        GARRelation historicalAddressMunRelation = new HistoricalAddressMunRelation();

        if (hierarchicalStreamReader.getAttribute("ISACTIVE").equals("1")) {
            if (checkMunRelation(hierarchicalStreamReader)) {
                relationMapper(hierarchicalStreamReader, actualAddressMunRelation, "ADDR_MUN_REL");
                return actualAddressMunRelation;
            } else {
                relationMapper(hierarchicalStreamReader, actualAddressAdmRelation, "ADDR_ADM_REL");
                return actualAddressAdmRelation;
            }
        } else {
            if (checkMunRelation(hierarchicalStreamReader)) {
                relationMapper(hierarchicalStreamReader, historicalAddressMunRelation, "HIST_ADDR_MUN_REL");
                return historicalAddressMunRelation;
            } else {
                relationMapper(hierarchicalStreamReader, historicalAddressAdmRelation, "HIST_ADDR_ADM_REL");
                return historicalAddressAdmRelation;
            }
        }
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARRelation.class);
    }

    private void relationMapper(HierarchicalStreamReader hierarchicalStreamReader, GARRelation relation, String recordType) {
        super.objectMapper(hierarchicalStreamReader, relation, recordType);
        if (checkEmpty(hierarchicalStreamReader, "PARENTOBJID")) {
            relation.setParentObjectId((Long) new LongConverter().fromString(hierarchicalStreamReader.getAttribute("PARENTOBJID")));
        }
        relation.setPath(hierarchicalStreamReader.getAttribute("PATH"));
    }

    private boolean checkMunRelation(HierarchicalStreamReader hierarchicalStreamReader) {
        return hierarchicalStreamReader.getAttribute("OKTMO") != null;
    }
}
