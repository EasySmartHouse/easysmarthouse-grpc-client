/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import net.easysmarthouse.ui.webui.client.rpc.SignalingService;
import com.github.creepid.grpc.client.GRPC;
import java.util.List;
import com.github.creepid.grpc.client.RequestHeader;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import net.easysmarthouse.provider.device.alarm.SignalingElement;

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
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "7D1B5E7FA8BAD85B4FA9E004DF645255");
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
