/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easysmarthouse.provider.device;

/**
 *
 * @author rusakovich
 */
public abstract class AbstractDevice {

    protected String label;
    protected String description;
    protected String address;

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

}
