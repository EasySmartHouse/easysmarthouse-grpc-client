/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.process;

import com.github.creepid.grpc.client.utils.collections.CollectionProcessor;

/**
 *
 * @author rusakovich
 */
public class StringConvertor implements CollectionProcessor<String, Object> {

    @Override
    public String getProcessedElement(Object obj) {
        if (obj == null) {
            return null;
        }

        return String.valueOf(obj);
    }

}
