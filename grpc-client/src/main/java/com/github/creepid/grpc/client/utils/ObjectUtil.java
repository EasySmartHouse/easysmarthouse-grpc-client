/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import java.math.BigDecimal;

/**
 *
 * @author rusakovich
 */
public class ObjectUtil {

    private ObjectUtil() {
    }

    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }

        if (obj instanceof Number) {
            Number num = (Number) obj;
            BigDecimal bdValue = BigDecimal.valueOf(num.doubleValue());
            return (bdValue.compareTo(BigDecimal.ZERO) == 0);
        }
        
        return false;
    }

}
