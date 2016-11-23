/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

/**
 *
 * @author rusakovich
 */
public interface CollectionFilter<T> {
    
    boolean isSuitable(T elem);
    
}
