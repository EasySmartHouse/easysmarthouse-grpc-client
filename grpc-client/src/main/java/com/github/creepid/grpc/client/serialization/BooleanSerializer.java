package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class BooleanSerializer extends AbstractSerializer {

    private static final String BOOLEAN_FALSE_VALUE = "0";
    private static final String BOOLEAN_TRUE_VALUE = "1";

    @Override
    boolean match(Class<?> cls) {
        return (cls == boolean.class) || (cls == Boolean.class);
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        return !BOOLEAN_FALSE_VALUE.equals(indexes.pop());
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        boolean boolValue = (boolean) value;
        if (methodType == Boolean.class) {
            strTableAware.addValueToIndexes(String.valueOf(strTableAware.getTypeIndex(Boolean.class)));
        }
        strTableAware.addValueToIndexes((boolValue) ? BOOLEAN_TRUE_VALUE : BOOLEAN_FALSE_VALUE);
        strTableAware.flushValue(boolValue);
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        strTableAware.addTypeIndex(methodType);
    }

}
