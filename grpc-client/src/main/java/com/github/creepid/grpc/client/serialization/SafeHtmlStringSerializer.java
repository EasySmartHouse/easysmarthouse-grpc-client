/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.util.ArrayDeque;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class SafeHtmlStringSerializer extends SerializerAdapter {

    private static final String SAFEHTML_UTILS_CLASS = "com.google.gwt.safehtml.shared.SafeHtmlUtils";
    private static final String SAFEHTML_STRING_CLASS_NAME = "com.google.gwt.safehtml.shared.SafeHtmlString";

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        //remove classes
        indexes.removeFirst();
        int valueIndex = Integer.valueOf(indexes.pollFirst());
        String value = stringTable.get(valueIndex - 1);
        return ReflectionUtil.staticInvokeAndGet(SAFEHTML_UTILS_CLASS, "fromString", value);
    }

    @Override
    boolean match(Class<?> cls) {
        if (cls == null) {
            return false;
        }

        return SAFEHTML_STRING_CLASS_NAME.equalsIgnoreCase(cls.getName());
    }

}
