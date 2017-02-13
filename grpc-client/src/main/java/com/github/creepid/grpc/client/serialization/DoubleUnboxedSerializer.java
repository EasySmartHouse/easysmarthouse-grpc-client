/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class DoubleUnboxedSerializer extends SerializerAdapter {

    @Override
    boolean match(Class<?> cls) {
        return (cls == double.class);
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        return Double.parseDouble(indexes.pop());
    }

}
