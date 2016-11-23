/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

import com.github.creepid.grpc.client.utils.collections.CollectionAware;
import java.util.Collection;

/**
 *
 * @author rusakovich
 */
public class DublicateElementsFilter<T> implements CollectionFilter<T>, CollectionAware {

    private Collection<T> collection;

    @Override
    public boolean isSuitable(T elem) {
        return !collection.contains(elem);
    }

    @Override
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
