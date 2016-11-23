/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author rusakovich
 */
public class Person implements IsSerializable {

    @Valid
    private Address address;

    @Valid
    private Map<String, Address> otherAddresses;

    @NotNull
    @Size(min = 4, message = "{custom.name.size.message}")
    private String name;

    private long ssn;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, Address> getOtherAddresses() {
        return otherAddresses;
    }

    public void setOtherAddresses(Map<String, Address> otherAddresses) {
        this.otherAddresses = otherAddresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSsn() {
        return ssn;
    }

    public void setSsn(long ssn) {
        this.ssn = ssn;
    }

}
