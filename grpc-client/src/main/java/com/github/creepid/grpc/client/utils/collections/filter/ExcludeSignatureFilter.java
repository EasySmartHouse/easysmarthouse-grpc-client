/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

import com.github.creepid.grpc.client.serialization.SerializabilityUtil;

/**
 *
 * @author rusakovich
 */
public class ExcludeSignatureFilter<T extends Class> implements CollectionFilter<T> {

    @Override
    public boolean isSuitable(T elem) {
        return SerializabilityUtil.excludeImplementationFromSerializationSignature(elem);
    }

}
