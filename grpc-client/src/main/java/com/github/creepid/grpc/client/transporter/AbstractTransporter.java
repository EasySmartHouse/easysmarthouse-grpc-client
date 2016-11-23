/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.transporter;

/**
 *
 * @author rusakovich
 */
public abstract class AbstractTransporter implements Transporter {

    protected String url;

    protected AbstractTransporter(String url) {
        this.url = url;
    }
}
