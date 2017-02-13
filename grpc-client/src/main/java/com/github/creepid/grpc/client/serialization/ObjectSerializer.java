/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;


/**
 *
 * @author rusakovich
 */
public class ObjectSerializer extends SerializerAdapter {

    @Override
    boolean match(Class<?> cls) {
        return (cls == Object.class);
    }

}
