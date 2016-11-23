/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.process;

import com.github.creepid.grpc.client.serialization.SerializabilityUtil;
import com.github.creepid.grpc.client.utils.collections.CollectionProcessor;

/**
 *
 * @author rusakovich
 */
public class GRpcEncodeClassProcessor implements CollectionProcessor<String, Class<?>> {

    @Override
    public String getProcessedElement(Class<?> cls) {
        if (cls.isPrimitive()){
            return SerializabilityUtil.getSerializedTypeName(cls);
        }
        return SerializabilityUtil.encodeSerializedInstanceReference(cls);
    }

}
