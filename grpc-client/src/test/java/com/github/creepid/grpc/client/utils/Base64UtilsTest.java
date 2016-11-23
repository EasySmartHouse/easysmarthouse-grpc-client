/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rusakovich
 */
public class Base64UtilsTest {
    
    public Base64UtilsTest() {
    }

    /**
     * Test of toBase64 method, of class Base64Utils.
     */
    @Test
    public void testToBase64_byteArr() {
        System.out.println("***** testToBase64_byteArr *****");
        assertNull(Base64Utils.toBase64(null));
        System.out.println(Base64Utils.toBase64("0".getBytes()));
    }

    /**
     * Test of longFromBase64 method, of class Base64Utils.
     */
    @Test
    public void testLongFromBase64() {
        System.out.println("***** testLongFromBase64 *****");
        long val = Base64Utils.longFromBase64("A");
        assertEquals(0l, val);
    }

    /**
     * Test of toBase64 method, of class Base64Utils.
     */
    @Test
    public void testToBase64_long() {
        System.out.println("***** testToBase64_long *****");
        String encoded = Base64Utils.toBase64(0);
        assertEquals("A", encoded);
    }

   

    
}
