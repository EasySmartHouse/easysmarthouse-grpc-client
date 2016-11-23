/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import static com.github.creepid.grpc.client.utils.ClassHelper.isNative;
import com.github.creepid.grpc.client.utils.ObjectUtil;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class NativeTypeSerializer extends AbstractSerializer {

    @Override
    boolean match(Class<?> cls) {
        if (!isNative(cls)) {
            return false;
        }

        Serializer[] exclude = {this};
        return SerializerContainer.findSerializerForClass(cls, exclude) == null;
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        if (ObjectUtil.isBlank(value)) {
            strTableAware.addValueToIndexes(encodeBlank(value));
            return;
        }

        strTableAware.addValueIndex(value);
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        //add method type param index
        strTableAware.addTypeIndex(methodType);
    }

}
