/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rusakovich
 */
public class EnumSerializer extends SerializerAdapter {

    //TODO replase with HashMap with keys as stringTable
    private Map<Integer, Object> storedEnums = new HashMap<>();

    @Override
    boolean match(Class<?> cls) {
        return cls.isEnum();
    }

    private void saveLastEnum(Object enumObj, List<String> stringTable) {
        storedEnums.put(stringTable.hashCode(), enumObj);
    }

    private Object getStored(List<String> stringTable) {
        return storedEnums.get(stringTable.hashCode());
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        Integer enumIndex = Integer.valueOf(indexes.pop());
        if (enumIndex < 0) {
            return getStored(stringTable);
        }

        String encoded = stringTable.get(enumIndex - 1);
        SerializedInstanceReference ref = SerializabilityUtil.decodeSerializedInstanceReference(encoded);

        if (!ref.getSignature().isEmpty()) {
            try {
                Class cls = Class.forName(ref.getName());
                Object[] enumConstants = cls.getEnumConstants();
                Integer ordinalIndex = Integer.parseInt(indexes.pop());
                Object enumConstant = enumConstants[ordinalIndex];
                saveLastEnum(enumConstant, stringTable);
                return enumConstant;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            return getStored(stringTable);
        }
    }

}
