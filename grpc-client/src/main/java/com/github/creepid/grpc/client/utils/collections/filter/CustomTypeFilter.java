/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

import static com.github.creepid.grpc.client.utils.ClassHelper.isCustom;

/**
 *
 * @author rusakovich
 */
public class CustomTypeFilter<T> implements CollectionFilter<T> {

    @Override
    public boolean isSuitable(T elem) {
        if (elem == null) {
            return false;
        }

        if (elem instanceof Class) {
            return isCustom((Class) elem);
        } else {
            return isCustom(elem.getClass());
        }
    }

}
