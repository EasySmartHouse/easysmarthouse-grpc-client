/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 *
 * @author rusakovich
 */
public class FieldsNameComparator implements Comparator<Field> {

    @Override
    public int compare(Field one, Field another) {
        return one.getName().compareTo(another.getName());
    }

}
