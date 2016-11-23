/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class StringSerializer extends AbstractSerializer {

    private static final String STRING_NULL_VALUE = "0";

    @Override
    boolean match(Class<?> cls) {
        return cls == String.class;
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        String val = indexes.pop();
        if (STRING_NULL_VALUE.equals(val)) {
            return null;
        }
        return stringTable.get(Integer.valueOf(val) - 1);
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        strTableAware.addValueIndex(value);
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        //add method type param index
        strTableAware.addTypeIndex(methodType);
    }

}
