/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ginger.smarthome.ui.webui.client.rpc;

import by.ginger.smarthome.provider.device.alarm.SignalingElement;
import com.github.creepid.grpc.client.ServiceRelativePath;
import java.util.List;

/**
 *
 * @author rusakovich
 */
@ServiceRelativePath("signaling")
public interface SignalingService {

    public List<SignalingElement> getSignalingElements();
    
    public void setEnabled(String address, boolean enabled);

}
