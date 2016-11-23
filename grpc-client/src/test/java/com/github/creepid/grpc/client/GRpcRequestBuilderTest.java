/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rusakovich
 */
public class GRpcRequestBuilderTest {

    public GRpcRequestBuilderTest() {
    }

    /**
     * Test of getBody method, of class GRpcRequestBuilder.
     */
    @Test
    public void testGetBodySomeServiceGetSomeObjects() {
        System.out.println("***** testGetBodySomeServiceGetSomeObjects *****");
        GRpcSettings rpcSettings = new GRpcSettings();
        rpcSettings.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        rpcSettings.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "88F34AC4FDF43EE523111A221B46FB00");

        Method method = ReflectionUtil.getMethod("getSomeObjects", SomeService.class);
        GRpcRequestBuilder requestBuilder = new GRpcRequestBuilder(rpcSettings, SomeService.class, method, new Object[0]);

        String body = requestBuilder.getBody();
        assertEquals("7|0|4|http://localhost:8080/webui/webui/|88F34AC4FDF43EE523111A221B46FB00|com.github.creepid.grpc.client.SomeService|getSomeObjects|1|2|3|4|0|", body);
    }

    /**
     * Test of getBody method, of class GRpcRequestBuilder.
     */
    @Test
    public void testGetBodySomeServiceSetEnabled() {
        System.out.println("***** testGetBodySomeServiceSetEnabled *****");
        GRpcSettings rpcSettings = new GRpcSettings();
        rpcSettings.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        rpcSettings.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "88F34AC4FDF43EE523111A221B46FB00");

        Method method = ReflectionUtil.getMethod("setEnabled", SomeService.class);

        final Object[] args = {"address", true};
        GRpcRequestBuilder requestBuilder = new GRpcRequestBuilder(rpcSettings, SomeService.class, method, args);

        String body = requestBuilder.getBody();
        assertTrue(body.endsWith("|Z|address|1|2|3|4|2|5|6|7|1|"));
        assertTrue(body.startsWith("7|0|7|http://localhost:8080/webui/webui/|88F34AC4FDF43EE523111A221B46FB00|com.github.creepid.grpc.client.SomeService|setEnabled|java.lang.String"));
    }

    @Test
    public void testGetBodyGreetingServiceGreetServer() {
        System.out.println("***** testGetBodyGreetingServiceGreetServer *****");
        GRpcSettings rpcSettings = new GRpcSettings();
        rpcSettings.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        rpcSettings.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "88F34AC4FDF43EE523111A221B46FB00");

        Method method = ReflectionUtil.getMethod("greetServer", GreetingService.class);
        Person person = new Person();
        Object[] args = new Object[1];
        args[0] = person;
        person.setName("Hello");
        GRpcRequestBuilder requestBuilder = new GRpcRequestBuilder(rpcSettings, GreetingService.class, method, args);
        String body = requestBuilder.getBody();
        assertEquals("7|0|6|http://localhost:8080/webui/webui/|88F34AC4FDF43EE523111A221B46FB00|com.github.creepid.grpc.client.GreetingService|greetServer|com.github.creepid.grpc.client.Person/4125252205|Hello|1|2|3|4|1|5|5|0|6|0|A|", body);
    }

    /**
     * Test of getHeaders method, of class GRpcRequestBuilder.
     */
    @Test
    public void testGetHeaders() {
    }

}
