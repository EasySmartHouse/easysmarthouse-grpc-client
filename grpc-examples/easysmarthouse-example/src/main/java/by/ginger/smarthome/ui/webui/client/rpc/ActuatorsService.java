/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ginger.smarthome.ui.webui.client.rpc;

import by.ginger.smarthome.provider.device.actuator.Actuator;
import com.github.creepid.grpc.client.ServiceRelativePath;
import java.util.List;

/**
 *
 * @author rusakovich
 */
@ServiceRelativePath("actuators")
public interface ActuatorsService {

    public List<Actuator> getActuators();

    public void changeState(String address, Boolean state);

    public void changeState(String address, Double state);

}
