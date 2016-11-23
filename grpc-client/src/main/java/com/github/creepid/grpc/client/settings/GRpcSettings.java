/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.settings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rusakovich
 */
public class GRpcSettings implements Cloneable {

    private final Map<GRpcSetting, List<Object>> settingsMap = new HashMap<>();

    public Object put(GRpcSetting setting, Object value) {
        List<Object> list = null;
        if (settingsMap.containsKey(setting)) {
            list = settingsMap.get(setting);
        } else {
            list = new LinkedList<>();
        }
        list.add(value);
        settingsMap.put(setting, list);
        return value;
    }

    public Object get(GRpcSetting setting) {
        List<Object> settings = getSettings(setting);
        if (settings == null) {
            return null;
        }
        return settings.get(0);
    }

    public boolean contains(GRpcSetting setting) {
        return (getSettings(setting) != null);
    }

    public String getString(GRpcSetting setting) {
        Object val = get(setting);
        if (val == null) {
            return null;
        }
        return String.valueOf(val);
    }

    public List<Object> getSettings(GRpcSetting setting) {
        return settingsMap.get(setting);
    }

}
