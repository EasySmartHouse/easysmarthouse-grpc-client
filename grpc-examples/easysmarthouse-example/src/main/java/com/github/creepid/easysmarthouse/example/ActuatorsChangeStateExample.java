/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import by.ginger.smarthome.ui.webui.client.rpc.ActuatorsService;
import com.github.creepid.grpc.client.GRPC;
import com.github.creepid.grpc.client.RequestHeader;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;

/**
 *
 * @author rusakovich
 */
public class ActuatorsChangeStateExample {

    private static final int ITERATIONS = 20;

    private static final GRpcSettings SETTINGS = new GRpcSettings();
    private static ActuatorsService service;

    static {
        SETTINGS.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "716B355B2C588BA58EC6DD661C6990FB");
        SETTINGS.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER,
                new RequestHeader("Accept-Language", "en-gb,en;q=0.5"));

        service = (ActuatorsService) GRPC.create(ActuatorsService.class, SETTINGS);
    }

    public static void main(String[] args) {
         for (int i = 0; i < ITERATIONS; i++) {
            service.changeState("USB0:1503:5824:0", Boolean.TRUE);
        }
    }

}
