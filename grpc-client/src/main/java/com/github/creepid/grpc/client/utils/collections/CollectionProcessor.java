/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections;

/**
 *
 * @author rusakovich
 */
public interface CollectionProcessor<D, S> {

    public D getProcessedElement(S s);

}
