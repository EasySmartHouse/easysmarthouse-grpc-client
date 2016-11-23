/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import net.easysmarthouse.provider.device.sensor.PlainSensor;
import org.junit.Test;

/**
 *
 * @author rusakovich
 */
public class SerializabilityUtilTest {
    
    public SerializabilityUtilTest() {
    }

    /**
     * Test of decodeSerializedInstanceReference method, of class SerializabilityUtil.
     */
    @Test
    public void testDecodeSerializedInstanceReference() {
        System.out.println("***** testDecodeSerializedInstanceReference *****");
        SerializedInstanceReference decoded = SerializabilityUtil.decodeSerializedInstanceReference("java.util.ArrayList/4159755760");
        assertNotNull(decoded);
        assertEquals("java.util.ArrayList", decoded.getName());
        assertEquals("4159755760", decoded.getSignature());
    }

    /**
     * Test of encodeSerializedInstanceReference method, of class SerializabilityUtil.
     */
    @Test
    public void testEncodeSerializedInstanceReference() {
        System.out.println("***** testEncodeSerializedInstanceReference *****");
        PlainSensor plainSensor = new PlainSensor();
        String encoded = SerializabilityUtil.encodeSerializedInstanceReference(plainSensor.getClass());
        assertEquals("net.easysmarthouse.provider.device.sensor.PlainSensor/2203309546", encoded);
        
        ArrayList<PlainSensor> sensorsList = new ArrayList<PlainSensor>();
        encoded = SerializabilityUtil.getSerializedTypeName(sensorsList.getClass());
        assertEquals("java.util.ArrayList", encoded);
    }

   
    
}
