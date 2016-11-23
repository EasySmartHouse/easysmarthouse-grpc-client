/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.utils.Base64Utils;

/**
 *
 * @author rusakovich
 */
abstract class AbstractSerializer implements Serializer {

    abstract boolean match(Class<?> cls);

    protected String encodeBlank(Object object) {
        return Base64Utils.encodeBlank(object);
    }

}
