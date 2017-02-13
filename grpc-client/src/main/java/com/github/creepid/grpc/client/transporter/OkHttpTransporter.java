/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.transporter;

import com.github.creepid.grpc.client.RequestHeader;
import java.io.IOException;
import java.util.Set;
import okhttp3.*;

/**
 *
 * @author rusakovich
 */
public class OkHttpTransporter extends AbstractTransporter {

    private static final MediaType RPC_MEDIA_TYPE = MediaType.parse("text/x-gwt-rpc; charset=utf-8");

    public OkHttpTransporter(String url) {
        super(url);
    }

    @Override
    public String sendRequest(Set<RequestHeader> headers, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        for (RequestHeader header : headers) {
            builder.addHeader(header.getName(), header.getValue());
        }
        Request request = builder
                .url(url)
                .post(RequestBody.create(RPC_MEDIA_TYPE, body))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
