/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils;

/**
 *
 * @author rusakovich
 */
public class UrlUtil {

    private UrlUtil() {
    }

    public static String getFullUrl(String base, String relativePath) {
        StringBuilder reqBuilder = new StringBuilder(base);
        if (!base.endsWith("/")) {
            reqBuilder.append("/");
        }
        reqBuilder.append(relativePath.replaceFirst("/", ""));
        return reqBuilder.toString();
    }

}
