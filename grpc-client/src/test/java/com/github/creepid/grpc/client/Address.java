/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rusakovich
 */
public class Address implements IsSerializable {

    @NotNull
    public String street;

    public String zip;

}
