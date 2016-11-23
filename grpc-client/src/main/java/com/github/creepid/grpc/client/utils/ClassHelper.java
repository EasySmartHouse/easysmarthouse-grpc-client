/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rusakovich
 */
public class ClassHelper {

    private ClassHelper() {
    }

    private static final Map<Class, Class> WRAPPER_TYPES = new HashMap<>();

    static {
        WRAPPER_TYPES.put(Boolean.class, boolean.class);
        WRAPPER_TYPES.put(Byte.class, byte.class);
        WRAPPER_TYPES.put(Character.class, char.class);
        WRAPPER_TYPES.put(Double.class, double.class);
        WRAPPER_TYPES.put(Float.class, float.class);
        WRAPPER_TYPES.put(Integer.class, int.class);
        WRAPPER_TYPES.put(Long.class, long.class);
        WRAPPER_TYPES.put(Short.class, short.class);
    }

    /**
     * Find an instance of the specified annotation, walking up the inheritance
     * tree if necessary. Copied from {@link
     * com.google.gwt.i18n.rebind.AnnotationUtil}.
     *
     * <p>
     * The super chain is walked first, so if an ancestor superclass has the
     * requested annotation, it will be preferred over a directly implemented
     * interface.
     *
     * @param <T> Annotation type to search for
     * @param clazz root class to search, may be null
     * @param annotationClass class object of Annotation subclass to search for
     * @return the requested annotation or null if none
     */
    public static <T extends Annotation> T getClassAnnotation(Class<?> clazz,
            Class<T> annotationClass) {
        if (clazz == null) {
            return null;
        }
        T annot = clazz.getAnnotation(annotationClass);
        if (annot == null) {
            annot = getClassAnnotation(clazz.getSuperclass(), annotationClass);
            if (annot != null) {
                return annot;
            }
            for (Class<?> intf : clazz.getInterfaces()) {
                annot = getClassAnnotation(intf, annotationClass);
                if (annot != null) {
                    return annot;
                }
            }
        }
        return annot;
    }

    /**
     * Checks if specified method is XSRF protected based on the following
     * logic:
     *
     * <ul>
     * <li>Method level annotations override class level annotations.
     * <li>If method is annotated with {@code xsrfAnnotation} this method
     * returns {@code true}
     * <li>If method is annotated with {@code noXsrfAnnotation}, this method
     * returns {@code false}.
     * <li>If class is annotated with {@code xsrfAnnotation} and method is not
     * annotated, this method returns {@code true}.
     * <li>If class is annotated with {@code noXsrfAnnotation} and method is not
     * annotated, this method returns {@code false}.
     * <li>If no annotations are present and class has a method with return
     * value assignable from {@code xsrfTokenInterface}, this method returns
     * {@code true}.
     * <li>If no annotations are present this method returns {@code false}.
     * </ul>
     *
     * @see com.google.gwt.user.server.rpc.AbstractXsrfProtectedServiceServlet
     */
    public static boolean isMethodXsrfProtected(Method method,
            Class<? extends Annotation> xsrfAnnotation,
            Class<? extends Annotation> noXsrfAnnotation,
            Class<?> xsrfTokenInterface) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (method.getAnnotation(noXsrfAnnotation) != null
                || (ClassHelper.getClassAnnotation(
                        declaringClass, noXsrfAnnotation) != null
                && method.getAnnotation(xsrfAnnotation) == null)) {
            // XSRF protection is disabled
            return false;
        }
        if (ClassHelper.getClassAnnotation(declaringClass, xsrfAnnotation) != null
                || method.getAnnotation(xsrfAnnotation) != null) {
            return true;
        }
        // if no explicit annotation is given no XSRF token verification is done,
        // unless there's a method returning RpcToken in which case XSRF token
        // verification is performed for all methods
        Method[] classMethods = declaringClass.getMethods();
        for (Method classMethod : classMethods) {
            if (xsrfTokenInterface.isAssignableFrom(classMethod.getReturnType())
                    && !method.equals(classMethod)) {
                return true;
            }
        }
        return false;
    }

    public static Class<?>[] getClasses(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return new Class<?>[0];
        }

        List<Class<?>> classList = new ArrayList<>();
        for (Object obj : objs) {
            if (obj != null) {
                classList.add(obj.getClass());
            } else {
                classList.add(null);
            }
        }

        Class<?>[] clasesArr = new Class<?>[classList.size()];
        return classList.toArray(clasesArr);
    }

    private static boolean checkTypeStartsWithPackage(Class cls, List<String> types) {
        if (cls == null) {
            return false;
        }

        if (cls.getPackage() == null) {
            return false;
        }

        String packageName = cls.getPackage().getName();
        for (String nativePackage : types) {
            if (packageName.startsWith(nativePackage)) {
                return true;
            }
        }

        return false;
    }

    private static final List<String> NATIVE_PACKAGE_PREFIXES = Arrays.asList(
            new String[]{"java", "javax", "com.sun", "sun"});

    public static boolean isNative(Class cls) {
        return checkTypeStartsWithPackage(cls, NATIVE_PACKAGE_PREFIXES);
    }

    private static final List<String> GWT_PACKAGE_PREFIX = Arrays.asList(
            new String[]{"com.google.gwt"});

    public static boolean isGwt(Class cls) {
        return checkTypeStartsWithPackage(cls, GWT_PACKAGE_PREFIX);
    }

    public static boolean isCustom(Class cls) {
        return (!isNative(cls) && !isGwt(cls));
    }

    public static Class getBoxUnboxType(Class cls) {        
        List<Class> keys = new ArrayList<>(WRAPPER_TYPES.keySet());
        List<Class> values = new ArrayList<>(WRAPPER_TYPES.values());

        if (keys.contains(cls)) {
            int valIndex = keys.indexOf(cls);
            return values.get(valIndex);
        }

        if (values.contains(cls)) {
            int keyIndex = values.indexOf(cls);
            return keys.get(keyIndex);
        }

        return null;
    }

}
