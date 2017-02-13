/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class ArrayListSerializer extends SerializerAdapter {

    @Override
    public Object decode(final List<String> stringTable, ArrayDeque<String> indexes) {
        indexes.removeFirst();

        int arraySize = Integer.valueOf(indexes.pop());
        ArrayList list = new ArrayList(arraySize);

        //try to parse elements
        String elementClassIndex = null;
        while ((elementClassIndex = indexes.peek()) != null) {
            String elementClass = stringTable.get(Integer.parseInt(elementClassIndex) - 1);

            SerializedInstanceReference ref = SerializabilityUtil.decodeSerializedInstanceReference(elementClass);
            String name = ref.getName();
            if (!ref.getSignature().isEmpty()) {
                Serializer serializer = SerializerContainer.findSerializerForClass(ref.getName());
                list.add(serializer.decode(stringTable, indexes));
            } else {
                list.add(name);
            }

        }

        return list;
    }

    @Override
    boolean match(Class<?> cls) {
        return (cls == ArrayList.class);
    }

}
