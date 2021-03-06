/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import com.github.creepid.grpc.client.GRPC;
import com.github.creepid.grpc.client.RequestHeader;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import net.easysmarthouse.ui.webui.client.rpc.ActuatorsService;
import java.util.List;
import net.easysmarthouse.provider.device.actuator.Actuator;

/**
 *
 * @author rusakovich
 */
public class ActuatorsShowExample {

    private static final int ITERATIONS = 20;

    private static final GRpcSettings SETTINGS = new GRpcSettings();
    private static ActuatorsService service;

    static {
        SETTINGS.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "6B24294BDEE46C557C392AA0A803BCE9");
        SETTINGS.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER,
                new RequestHeader("Accept-Language", "en-gb,en;q=0.5"));

        service = (ActuatorsService) GRPC.create(ActuatorsService.class, SETTINGS);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            List<Actuator> actuators = service.getActuators();
            for (Actuator actuator : actuators) {
                System.out.println("------------------------------");
                System.out.println("Address: " + actuator.getAddress());
                System.out.println("Description: " + actuator.getDescription());
                System.out.println("Label: " + actuator.getLabel());
                System.out.println("Value: " + actuator.getState());
            }
            Thread.sleep(1000l);
        }
    }

}
