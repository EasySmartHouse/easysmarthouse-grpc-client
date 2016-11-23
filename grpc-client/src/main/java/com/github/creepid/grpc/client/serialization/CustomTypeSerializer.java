/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import com.github.creepid.grpc.client.utils.Base64Utils;
import static com.github.creepid.grpc.client.utils.ClassHelper.isCustom;
import com.github.creepid.grpc.client.utils.ObjectUtil;
import com.github.creepid.grpc.client.utils.ReflectionUtil;
import static com.github.creepid.grpc.client.utils.collections.CollectionsHelper.sort;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rusakovich
 */
public class CustomTypeSerializer extends AbstractSerializer {

    @Override
    boolean match(Class<?> cls) {
        if (!isCustom(cls)) {
            return false;
        }

        Serializer[] exclude = {this};
        return SerializerContainer.findSerializerForClass(cls, exclude) == null;
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        String encodedClass = stringTable.get(Integer.valueOf(indexes.pop()) - 1);
        SerializedInstanceReference ref = SerializabilityUtil.decodeSerializedInstanceReference(encodedClass);
        String className = ref.getName();

        Object decoded = null;
        try {
            Class cls = Class.forName(className);
            decoded = ReflectionUtil.createInstance(cls);

            List<Field> fields = ReflectionUtil.getFields(cls);
            for (Field field : fields) {
                Serializer serializer = SerializerContainer.findSerializerForClass(field.getType());
                Object fieldObj = serializer.decode(stringTable, indexes);
                field.set(decoded, fieldObj);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return decoded;
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        if (ObjectUtil.isBlank(value)) {
            strTableAware.addValueToIndexes(encodeBlank(value));
            return;
        }

        Map sortedFields = sort(ReflectionUtil.getFieldsWithVals(value));
        Set keys = sortedFields.keySet();
        for (Object key : keys) {
            Object fieldValue = sortedFields.get(key);
            //check if elemnts is blank
            if (ObjectUtil.isBlank(fieldValue)) {
                //encode blank field value
                strTableAware.addValueToIndexes(Base64Utils.encodeBlank(fieldValue));
            } else {
                //or get the index of the value from the string table
                strTableAware.addValueIndex(fieldValue);
            }
        }
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        //add method type param index
        strTableAware.addTypeIndex(methodType);
        //add runtime type param index
        strTableAware.addTypeIndex(runtimeType);
    }

}
