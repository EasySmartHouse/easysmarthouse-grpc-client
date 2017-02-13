/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import com.github.creepid.grpc.client.settings.GeneralSearchPolicy;
import com.github.creepid.grpc.client.settings.GrpcRelativePathSearchPolicy;
import com.github.creepid.grpc.client.settings.GwtRelativePathSearchPolicy;
import com.github.creepid.grpc.client.settings.PropertySearchPolicy;
import com.github.creepid.grpc.client.transporter.ApacheHttpTransporter;
import com.github.creepid.grpc.client.transporter.Transporter;
import static com.github.creepid.grpc.client.utils.ReflectionUtil.*;
import com.github.creepid.grpc.client.utils.UrlUtil;
import java.lang.reflect.Proxy;

/**
 *
 * @author rusakovich
 */
public class GRPC {

    private static Class<? extends Transporter> transporterClass = ApacheHttpTransporter.class;
    private static final GeneralSearchPolicy<String> relativePathSearchPolicy = new GeneralSearchPolicy<>(
            new GwtRelativePathSearchPolicy(), new GrpcRelativePathSearchPolicy());

    private GRPC() {
    }

    public static Object create(Class<?> serviceInterface, GRpcSettings rpcSettings) {
        String url = rpcSettings.getString(GRpcSetting.BASE_URL);
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Base application URL is mandatory. Please, set GRpcSetting.BASE_URL property");
        }

        if (!rpcSettings.contains(GRpcSetting.POLICY_FILE_STRONG_NAME)) {
            throw new IllegalArgumentException("Policy file name is mandatory. Please, set GRpcSetting.BASE_URL property");
        }

        String relativePath = (rpcSettings.contains(GRpcSetting.SERVICE_RELATIVE_PATH))
                ? rpcSettings.getString(GRpcSetting.SERVICE_RELATIVE_PATH)
                : relativePathSearchPolicy.getProperty(serviceInterface);
        if (relativePath == null) {
            throw new IllegalStateException("Cannot find service relative path. Please, set GRpcSetting.SERVICE_RELATIVE_PATH property or set ServiceRelativePath annotation");
        }

        Class[] interfaces = new Class[1];
        interfaces[0] = serviceInterface;

        Transporter transporter = createInstance(transporterClass, UrlUtil.getFullUrl(url, relativePath));

        rpcSettings.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER, new RequestHeader("X-GWT-Module-Base", url));
        RpcServiceInvocationProxy invocationProxy = new RpcServiceInvocationProxy(serviceInterface, rpcSettings);
        invocationProxy.setTransporter(transporter);

        return Proxy.newProxyInstance(serviceInterface.getClassLoader(), interfaces, invocationProxy);
    }

    public static void setTransporterClass(Class<? extends Transporter> transporterClass) {
        GRPC.transporterClass = transporterClass;
    }

    public static void addRelativePathSearchPolicy(PropertySearchPolicy policy) {
        relativePathSearchPolicy.addSearchPolicy(policy);
    }

}
