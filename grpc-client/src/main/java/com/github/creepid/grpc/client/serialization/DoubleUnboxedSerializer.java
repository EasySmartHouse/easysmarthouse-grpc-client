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
public class DoubleUnboxedSerializer extends AbstractSerializer {

    @Override
    boolean match(Class<?> cls) {
        return (cls == double.class);
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        return Double.parseDouble(indexes.pop());
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
