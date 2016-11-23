/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rusakovich
 */
public class DoubleBoxedSerializer extends AbstractSerializer {

    //TODO replase with HashMap with keys as stringTable
    private Map<Integer, Double> stored = new HashMap<>();

    private void saveLast(Double value, List<String> stringTable) {
        stored.put(stringTable.hashCode(), value);
    }

    private Double getStored(List<String> stringTable) {
        return stored.get(stringTable.hashCode());
    }

    @Override
    boolean match(Class<?> cls) {
        return (cls == Double.class);
    }

    private boolean isNegativeReference(String value) {
        if (value == null) {
            return false;
        }

        return (value.startsWith("-") && !value.contains("."));
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        //remove class reference
        String refValue = indexes.poll();
        if (isNegativeReference(refValue)) {
            return getStored(stringTable);
        }

        Double dblValue = Double.parseDouble(indexes.pop());
        saveLast(dblValue, stringTable);
        return dblValue;
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
