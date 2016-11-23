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
public interface Serializer {

    public Object decode(List<String> stringTable, ArrayDeque<String> indexes);

    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware);

    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware);

}
