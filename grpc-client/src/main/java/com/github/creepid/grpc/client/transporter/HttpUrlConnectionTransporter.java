/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.transporter;

import com.github.creepid.grpc.client.RequestHeader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 *
 * @author rusakovich
 */
public class HttpUrlConnectionTransporter extends AbstractTransporter {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String METHOD = "POST";
    private static final String CHARSET = "UTF-8";

    public HttpUrlConnectionTransporter(String url) {
        super(url);
    }

    private void writeBody(HttpURLConnection conn, String body) throws IOException {
        try (OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, CHARSET))) {
            writer.write(body != null ? body : "");
            writer.flush();
        }
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        InputStream is;
        if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }
        int ch;
        while ((ch = is.read()) != -1) {
            responseBuilder.append((char) ch);
        }
        return responseBuilder.toString();
    }

    @Override
    public String sendRequest(Set<RequestHeader> headers, String body) throws IOException {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            for (RequestHeader header : headers) {
                conn.setRequestProperty(header.getName(), header.getValue());
            }

            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod(METHOD);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            writeBody(conn, body);

            return readResponse(conn);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

}
