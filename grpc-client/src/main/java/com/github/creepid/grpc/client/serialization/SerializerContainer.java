/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.util.HashSet;
import java.util.Set;
import javassist.Modifier;
import org.reflections.Reflections;

/**
 *
 * @author rusakovich
 */
public class SerializerContainer {

    private static final Set<AbstractSerializer> SERIALIZERS = new HashSet<>();

    private static void registerAllSerializersStatic() {
        SERIALIZERS.add(new ArrayListSerializer());
        SERIALIZERS.add(new BooleanSerializer());
        SERIALIZERS.add(new CustomTypeSerializer());
        SERIALIZERS.add(new DoubleBoxedSerializer());
        SERIALIZERS.add(new DoubleUnboxedSerializer());
        SERIALIZERS.add(new EnumSerializer());
        SERIALIZERS.add(new LinkedListSerializer());
        SERIALIZERS.add(new LongSerializer());
        SERIALIZERS.add(new NativeTypeSerializer());
        SERIALIZERS.add(new ObjectSerializer());
        SERIALIZERS.add(new SafeHtmlStringSerializer());
        SERIALIZERS.add(new StringSerializer());
    }

    private static void registerAllSerializersDynamic(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends AbstractSerializer>> serializerClasses = reflections.getSubTypesOf(AbstractSerializer.class);
        for (Class<? extends AbstractSerializer> serializerClass : serializerClasses) {
            if (!Modifier.isAbstract(serializerClass.getModifiers())) {
                SERIALIZERS.add(ReflectionUtil.createInstance(serializerClass));
            }
        }
    }

    static {
        registerAllSerializersDynamic("com.github.creepid.grpc.client.serialization");
        if (SERIALIZERS.isEmpty()) {
            registerAllSerializersStatic();
        }
    }

    private SerializerContainer() {
    }

    private static boolean checkIn(Serializer[] serializers, Serializer target) {
        if (serializers == null || serializers.length == 0) {
            return false;
        }

        for (Serializer serializer : serializers) {
            if (serializer == target) {
                return true;
            }
        }
        return false;
    }

    public static Serializer findSerializerForClass(Class<?> cls, Serializer... exclude) {
        for (AbstractSerializer serializer : SERIALIZERS) {
            if (checkIn(exclude, serializer)) {
                continue;
            }

            if (serializer.match(cls)) {
                return serializer;
            }
        }
        return null;
    }

    public static Serializer findSerializerForClass(String className, Serializer... exclude) {
        try {
            Class cls = Class.forName(className);
            return findSerializerForClass(cls, exclude);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot find given class [" + className + "]", ex);
        }
    }

}
