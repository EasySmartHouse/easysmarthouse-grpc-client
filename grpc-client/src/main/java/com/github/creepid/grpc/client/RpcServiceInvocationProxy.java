/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.github.creepid.grpc.client.settings.GRpcSettings;
import com.github.creepid.grpc.client.transporter.Transporter;
import java.lang.reflect.Method;
import java.util.Set;

/**
 *
 * @author rusakovich
 */
class RpcServiceInvocationProxy implements java.lang.reflect.InvocationHandler {
    
    private final RpcResponseParser reponseParser = new GRpcResponseParser();

    private final Class serviceInterface;
    private final GRpcSettings rpcSettings;

    private Transporter transporter;

    public RpcServiceInvocationProxy(Class serviceInterface, GRpcSettings rpcSettings) {
        this.serviceInterface = serviceInterface;
        this.rpcSettings = rpcSettings;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequestBuilder requestBuilder = new GRpcRequestBuilder(
                rpcSettings,
                serviceInterface,
                method,
                args);
        
        String reqBody = requestBuilder.getBody();
        Set<RequestHeader> headers = requestBuilder.getHeaders();
        String response = transporter.sendRequest(headers, reqBody);
        return reponseParser.getResponseObject(response);
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

}
