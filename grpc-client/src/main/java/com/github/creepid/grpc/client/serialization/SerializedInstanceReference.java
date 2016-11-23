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
/**
 * Interface for describing a serialized instance reference reference.
 */
public interface SerializedInstanceReference {

    static final String SERIALIZED_REFERENCE_SEPARATOR = "/";

    /**
     * @return name of the type
     */
    String getName();

    /**
     * @return signature of the instance reference
     */
    String getSignature();
}
