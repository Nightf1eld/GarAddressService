package com.tkbbank.address_service.entities.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.tkbbank.address_service.entities.Dictionary;
import com.tkbbank.address_service.entities.utils.GARDictionary;

public class GARDictionaryConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GARDictionary dictionary = new Dictionary();
        dictionaryMapper(hierarchicalStreamReader, dictionary);
        return dictionary;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(GARDictionary.class);
    }

    private void dictionaryMapper(HierarchicalStreamReader hierarchicalStreamReader, GARDictionary dictionary) {
        dictionary.setType(hierarchicalStreamReader.getNodeName());

        if (checkEmpty(hierarchicalStreamReader, "SHORTNAME")) {
            dictionary.setCode(hierarchicalStreamReader.getAttribute("SHORTNAME"));
        } else if (checkEmpty(hierarchicalStreamReader, "CODE")) {
            dictionary.setCode(hierarchicalStreamReader.getAttribute("CODE"));
        } else if (checkEmpty(hierarchicalStreamReader, "LEVEL")) {
            dictionary.setCode(hierarchicalStreamReader.getAttribute("LEVEL"));
        } else {
            dictionary.setCode(hierarchicalStreamReader.getAttribute(null));
        }
        if (checkEmpty(hierarchicalStreamReader, "NAME")) {
            dictionary.setValue(hierarchicalStreamReader.getAttribute("NAME"));
        }
        if (checkEmpty(hierarchicalStreamReader, "DESC")) {
            dictionary.setDescription(hierarchicalStreamReader.getAttribute("DESC"));
        }
        if (checkEmpty(hierarchicalStreamReader, "LEVEL")) {
            dictionary.setLevel((Integer) new IntConverter().fromString(hierarchicalStreamReader.getAttribute("LEVEL")));
        }
        if (checkEmpty(hierarchicalStreamReader, "ISACTIVE")) {
            dictionary.setIsActive((Boolean) new BooleanConverter("true", "false", false).fromString(hierarchicalStreamReader.getAttribute("ISACTIVE")));
        }
    }

    private boolean checkEmpty(HierarchicalStreamReader hierarchicalStreamReader, String attribute) {
        return hierarchicalStreamReader.getAttribute(attribute) != null && !hierarchicalStreamReader.getAttribute(attribute).isEmpty();
    }
}
