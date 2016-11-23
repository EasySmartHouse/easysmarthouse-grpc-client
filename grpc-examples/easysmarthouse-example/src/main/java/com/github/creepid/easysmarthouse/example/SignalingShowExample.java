/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import by.ginger.smarthome.provider.device.alarm.SignalingElement;
import by.ginger.smarthome.ui.webui.client.rpc.SignalingService;
import com.github.creepid.grpc.client.GRPC;
import java.util.List;
import com.github.creepid.grpc.client.RequestHeader;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;

/**
 *
 * @author rusakovich
 */
public class SignalingShowExample {

    private static final int ITERATIONS = 20;

    private static final GRpcSettings SETTINGS = new GRpcSettings();
    private static SignalingService service;

    static {
        SETTINGS.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "4B4695FF053165CE27E233895D521C84");
        SETTINGS.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER,
                new RequestHeader("Accept-Language", "en-gb,en;q=0.5"));

        service = (SignalingService) GRPC.create(SignalingService.class, SETTINGS);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < ITERATIONS; i++) {
            List<SignalingElement> elements = service.getSignalingElements();
            for (SignalingElement element : elements) {
                System.out.println("------------------------------");
                System.out.println("Key address: " + element.getKeyAddress());
                System.out.println("Alarm: " + element.isAlarm());
                System.out.println("Enabled: " + element.isEnabled());
            }
            Thread.sleep(1000l);
        }
    }

}
