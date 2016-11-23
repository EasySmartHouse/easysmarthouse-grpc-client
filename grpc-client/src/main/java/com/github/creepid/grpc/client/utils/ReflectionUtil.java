/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.Modifier;

/**
 *
 * @author rusakovich
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    private static void addFields(final Class cls, List<Field> fields) {
        Field[] valueObjFields = cls.getDeclaredFields();

        List<Field> currClassFields = new ArrayList<>();
        for (int i = 0; i < valueObjFields.length; i++) {
            if (!Modifier.isStatic(valueObjFields[i].getModifiers())) {
                valueObjFields[i].setAccessible(true);
                currClassFields.add(valueObjFields[i]);
            }
        }

        Collections.sort(currClassFields, new FieldsNameComparator());
        fields.addAll(currClassFields);
    }

    public static List<Field> getFields(final Class sourceClass) {
        List<Field> fields = new ArrayList<Field>();
        Class cls = sourceClass;

        while (cls != null) {
            addFields(cls, fields);
            cls = cls.getSuperclass();
        }

        return fields;
    }

    public static Map<String, Object> getFieldsWithVals(final Object valueObj) {
        if (valueObj == null) {
            return Collections.EMPTY_MAP;
        }

        Class c1 = valueObj.getClass();

        Map fieldMap = new HashMap();
        Field[] valueObjFields = c1.getDeclaredFields();

        // compare values now
        for (int i = 0; i < valueObjFields.length; i++) {
            String fieldName = valueObjFields[i].getName();
            valueObjFields[i].setAccessible(true);

            try {
                Object newObj = valueObjFields[i].get(valueObj);

                fieldMap.put(fieldName, newObj);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return fieldMap;
    }

    public static <T> T createInstance(Class<T> cls, Object... args) {
        try {

            Constructor<T> cons = null;
            if (args == null || args.length == 0) {
                cons = cls.getConstructor();
            } else {
                Class<?>[] types = new Class<?>[args.length];
                for (int i = 0; i < types.length; i++) {
                    Class<?> type = args[i].getClass();
                    types[i] = type;
                }
                cons = cls.getConstructor(types);
            }

            cons.setAccessible(true);
            T destination = cons.newInstance(args);
            return destination;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T createInstance(String className, Object... args) {
        try {
            Class<T> clazz = (Class<T>) Class.forName(className);
            return createInstance(clazz, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Method getMethod(String methodName, Class cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static <T> Object invokeAndGet(Class<T> cls, T t, String methodName, Object... args) {
        try {
            Method method = getMethod(methodName, cls);
            method.setAccessible(true);
            if (method == null) {
                return null;
            }
            return method.invoke(t, args);
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> Object staticInvokeAndGet(Class<T> cls, String methodName, Object... args) {
        return invokeAndGet(cls, null, methodName, args);
    }

    public static <T> Object staticInvokeAndGet(String className, String methodName, Object... args) {
        try {
            Class cls = Class.forName(className);
            return invokeAndGet(cls, null, methodName, args);
        } catch (Exception ex) {
            return null;
        }
    }

}
