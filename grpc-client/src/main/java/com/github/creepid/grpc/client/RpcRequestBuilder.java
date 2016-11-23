/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import java.util.Set;

/**
 *
 * @author rusakovich
 */
public interface RpcRequestBuilder {
    
    public String getBody();
    
    public Set<RequestHeader> getHeaders();
    
}
