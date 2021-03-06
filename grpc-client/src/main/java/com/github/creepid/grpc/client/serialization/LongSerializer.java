/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import com.github.creepid.grpc.client.utils.Base64Utils;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class LongSerializer extends SerializerAdapter {

    @Override
    boolean match(Class<?> cls) {
        return (cls == Long.class) || (cls == long.class);
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        return Base64Utils.longFromBase64(indexes.pop());
    }

}
