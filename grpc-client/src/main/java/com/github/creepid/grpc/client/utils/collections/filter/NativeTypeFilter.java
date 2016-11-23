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
public class NativeTypeFilter<T> implements CollectionFilter<T> {

    private CollectionFilter<T> customTypeFilter;

    public NativeTypeFilter() {
        this.customTypeFilter = new CustomTypeFilter<>();
    }

    @Override
    public boolean isSuitable(T elem) {
        return !customTypeFilter.isSuitable(elem);
    }

}
