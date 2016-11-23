/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author rusakovich
 */
@RemoteServiceRelativePath("some")
public interface SomeService extends RemoteService {

    public String getSomeObjects();

    public void setEnabled(String address, boolean enabled);

}
