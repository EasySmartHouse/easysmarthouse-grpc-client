/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.transporter;

import com.github.creepid.grpc.client.RequestHeader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author rusakovich
 */
public class ApacheHttpTransporter extends AbstractTransporter {

    private final HttpClient client = HttpClientBuilder.create().build();
    private final HttpPost post;

    public ApacheHttpTransporter(String url) {
        super(url);
        this.post = new HttpPost(url);
    }

    @Override
    public String sendRequest(Set<RequestHeader> headers, String body) throws IOException {
        try {
            for (RequestHeader header : headers) {
                post.setHeader(header.getName(), header.getValue());
            }
            HttpEntity bodyEntity = new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8));
            post.setEntity(bodyEntity);
            HttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException | ParseException ex) {
            throw new IOException(ex);
        }
    }

}
