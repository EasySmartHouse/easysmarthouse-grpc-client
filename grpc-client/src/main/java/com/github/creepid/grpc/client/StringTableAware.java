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
public interface StringTableAware {

    public void addTypeIndex(Class<?> type);

    public int getTypeIndex(Class<?> type);

    public void addValueIndex(Object value);

    public void addValueToIndexes(String value);

    public void flushValue(Object value);

    public void flushType(Class<?> type);

}
