/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class GeneralSearchPolicy<P> implements PropertySearchPolicy<P> {

    private final List<PropertySearchPolicy<P>> policies;

    public GeneralSearchPolicy(PropertySearchPolicy<P>... searchPolicies) {
        this.policies = new ArrayList<>();
        this.policies.addAll(Arrays.asList(searchPolicies));
    }

    @Override
    public P getProperty(Class<?> serviceInterface) {
        for (PropertySearchPolicy<P> policy : policies) {
            P p = policy.getProperty(serviceInterface);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    public void addSearchPolicy(PropertySearchPolicy<P> policy) {
        policies.add(policy);
    }

}
