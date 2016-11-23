/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import com.github.creepid.grpc.client.SomeObject;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rusakovich
 */
public class ReflectionUtilTest {
    
    public ReflectionUtilTest() {
    }

    /**
     * Test of getFieldNamesAndValues method, of class ReflectionUtil.
     */
    @Test
    public void testGetFieldNamesAndValues() throws Exception {
        System.out.println("***** testGetFieldNamesAndValues *****");
        SomeObject some = new SomeObject();
        Map fields = ReflectionUtil.getFieldsWithVals(some);
        assertEquals(3, fields.size());
        assertEquals(0.0d, fields.get("someDouble"));
        assertEquals(null, fields.get("someString"));
        assertEquals(null, fields.get("someEnum"));
    }
    
}
