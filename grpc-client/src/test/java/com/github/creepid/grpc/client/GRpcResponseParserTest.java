/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author rusakovich
 */
public class GRpcResponseParserTest {

    public GRpcResponseParserTest() {
    }

    /**
     * Test of getResponse method, of class GRpcResponseParser.
     */
    @Test
    public void testGetSafeHtmlResponse() throws Exception {
        System.out.println("***** testGetSafeHtmlResponse *****");
        String response = "//OK[2,1,[\"com.google.gwt.safehtml.shared.SafeHtmlString/235635043\",\"Hello, Hello!\"],0,7]";

        GRpcResponseParser parser = new GRpcResponseParser();
        Object responseObject = parser.getResponseObject(response);
        assertEquals(Class.forName("com.google.gwt.safehtml.shared.SafeHtmlString"), responseObject.getClass());
        assertEquals(((SafeHtml) responseObject).asString(), "Hello, Hello!");
    }

    @Test
    public void testMonitoringResponse() throws Exception {
        System.out.println("***** testMonitoringResponse *****");
        String response = "//OK[16,16,15,766.4,2,3,2,14,14,13,89.3,-7,2,12,12,11,62.17344718340688,1,3,2,10,10,9,43.984163794839574,-3,2,8,5,7,12.3,-3,2,6,5,4,2536.3311450379806,0,3,2,6,1,[\"java.util.ArrayList/4159755760\",\"net.easysmarthouse.provider.device.sensor.PlainSensor/2203309546\",\"net.easysmarthouse.provider.device.sensor.SensorType/1126924439\",\"C2000801AC339F10\",\"Temperature sensor outside\",\"Temperature sensor 1\",\"EC000801AC673410\",\"Temperature sensor 2\",\"USB0:29697:3141:0\",\"Server temperature sensor\",\"D4500801BC389D10\",\"Humidity sensor outdoor\",\"D4500801BC389D17\",\"Humidity sensor in room\",\"A4500801DC389D16\",\"Pressure sensor outdoor\"],0,7]";

        GRpcResponseParser parser = new GRpcResponseParser();
        Object responseObject = parser.getResponseObject(response);
        assertTrue(responseObject instanceof ArrayList);
        ArrayList list = (ArrayList) responseObject;
        assertEquals(6, list.size());
    }

}
