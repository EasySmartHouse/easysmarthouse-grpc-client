/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

/**
 *
 * @author rusakovich
 */
public class IdleStringTableAware implements StringTableAware {

    @Override
    public void addTypeIndex(Class<?> type) {
    }

    @Override
    public void addValueIndex(Object value) {
    }

    @Override
    public void addValueToIndexes(String value) {
    }

    @Override
    public void flushValue(Object value) {
    }

    @Override
    public void flushType(Class<?> type) {
    }

    @Override
    public int getTypeIndex(Class<?> type) {
        return Integer.MIN_VALUE;
    }

}
