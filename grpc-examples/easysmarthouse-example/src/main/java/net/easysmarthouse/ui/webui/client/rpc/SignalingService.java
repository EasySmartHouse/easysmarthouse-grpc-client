/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easysmarthouse.ui.webui.client.rpc;

import com.github.creepid.grpc.client.ServiceRelativePath;
import java.util.List;
import net.easysmarthouse.provider.device.alarm.SignalingElement;

/**
 *
 * @author rusakovich
 */
@ServiceRelativePath("signaling")
public interface SignalingService {

    public List<SignalingElement> getSignalingElements();
    
    public void setEnabled(String address, boolean enabled);

}
