/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import by.ginger.smarthome.provider.device.sensor.Sensor;
import by.ginger.smarthome.ui.webui.client.rpc.MonitoringService;
import com.github.creepid.grpc.client.GRPC;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import com.github.creepid.grpc.client.RequestHeader;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class MonitoringShowExample {

    private static final int ITERATIONS = 20;

    private static final GRpcSettings SETTINGS = new GRpcSettings();
    private static MonitoringService service;

    static {
        SETTINGS.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "7AE96959866EBB432818F011EF52F86C");
        SETTINGS.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER,
                new RequestHeader("Accept-Language", "en-gb,en;q=0.5"));

        service = (MonitoringService) GRPC.create(MonitoringService.class, SETTINGS);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            List<Sensor> sensors = service.getSensors();
            for (Sensor sensor : sensors) {
                System.out.println("------------------------------");
                System.out.println("Address: " + sensor.getAddress());
                System.out.println("Description: " + sensor.getDescription());
                System.out.println("Label: " + sensor.getLabel());
                System.out.println("Value: " + sensor.getValue());
            }
            Thread.sleep(1000l);
        }
    }

}
