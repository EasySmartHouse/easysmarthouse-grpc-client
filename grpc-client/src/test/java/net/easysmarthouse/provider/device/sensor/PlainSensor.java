/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easysmarthouse.provider.device.sensor;

import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class PlainSensor extends AbstractSensor {

    private SensorType sensorType;
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void main(String[] args) {
        List<Field> fields = ReflectionUtil.getFields(PlainSensor.class);
        Collections.reverse(fields);
        System.out.println("1111");
    }

}
