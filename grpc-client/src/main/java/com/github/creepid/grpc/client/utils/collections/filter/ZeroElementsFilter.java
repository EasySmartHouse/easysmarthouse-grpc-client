/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

import java.math.BigDecimal;

/**
 *
 * @author rusakovich
 */
public class ZeroElementsFilter<T> implements CollectionFilter<T> {

    @Override
    public boolean isSuitable(T elem) {
        if (elem instanceof Number) {
            Number num = (Number) elem;
            BigDecimal bdValue = BigDecimal.valueOf(num.doubleValue());
            return !(bdValue.compareTo(BigDecimal.ZERO) == 0);
        }
        return true;
    }

}
