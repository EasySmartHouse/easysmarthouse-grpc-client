/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.settings;

import static com.github.creepid.grpc.client.utils.ClassHelper.getClassAnnotation;
import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.lang.annotation.Annotation;

/**
 *
 * @author rusakovich
 */
public class GwtRelativePathSearchPolicy implements PropertySearchPolicy<String> {

    private static final String GWT_RELATIVE_PATH_ANNOTATION_CLASS_NAME = "com.google.gwt.user.client.rpc.RemoteServiceRelativePath";

    private String getGwtRelativePath(Class<?> serviceInterface) {
        try {
            Class<Annotation> relativePathAnnotClass = (Class<Annotation>) Class.forName(GWT_RELATIVE_PATH_ANNOTATION_CLASS_NAME);
            Annotation relativePathAnnot = getClassAnnotation(serviceInterface, relativePathAnnotClass);
            return (String) ReflectionUtil.invokeAndGet(relativePathAnnotClass, relativePathAnnot, "value");
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String getProperty(Class<?> serviceInterface) {
        return getGwtRelativePath(serviceInterface);
    }

}
