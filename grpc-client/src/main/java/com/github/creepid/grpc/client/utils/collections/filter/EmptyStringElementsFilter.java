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
public class EmptyStringElementsFilter<T> implements CollectionFilter<T> {

    @Override
    public boolean isSuitable(T elem) {
        if (elem instanceof String) {
            String str = (String) elem;
            return !str.isEmpty();
        }
        return true;
    }

}
