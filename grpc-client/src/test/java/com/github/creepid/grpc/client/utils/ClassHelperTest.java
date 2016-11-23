/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

import com.github.creepid.grpc.client.SomeObject;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rusakovich
 */
public class ClassHelperTest {

    public ClassHelperTest() {
    }

    @Test
    public void testIsCustomType_Class() {
        System.out.println("***** testIsCustomType_Class *****");
        boolean custom = true;
        custom = ClassHelper.isCustom(String.class);
        assertFalse(custom);

        custom = ClassHelper.isCustom(List.class);
        assertFalse(custom);

        custom = ClassHelper.isCustom(ArrayList.class);
        assertFalse(custom);

        custom = ClassHelper.isCustom(Boolean.class);
        assertFalse(custom);

        custom = ClassHelper.isCustom(SomeObject.class);
        assertTrue(custom);
    }

}
