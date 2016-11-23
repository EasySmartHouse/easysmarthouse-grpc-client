/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.settings;

import com.github.creepid.grpc.client.ServiceRelativePath;
import static com.github.creepid.grpc.client.utils.ClassHelper.getClassAnnotation;

/**
 *
 * @author rusakovich
 */
public class GrpcRelativePathSearchPolicy implements PropertySearchPolicy<String> {

    private String getGRpcRelativePath(Class<?> serviceInterface) {
        ServiceRelativePath relativePathAnnot = getClassAnnotation(serviceInterface, ServiceRelativePath.class);
        if (relativePathAnnot != null) {
            return relativePathAnnot.value();
        }
        return null;
    }

    @Override
    public String getProperty(Class<?> serviceInterface) {
        return getGRpcRelativePath(serviceInterface);
    }

}
