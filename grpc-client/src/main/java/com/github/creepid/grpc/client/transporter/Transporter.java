/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.transporter;

import com.github.creepid.grpc.client.RequestHeader;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author rusakovich
 */
public interface Transporter {

    public String sendRequest(Set<RequestHeader> headers, String body) throws IOException;

}
