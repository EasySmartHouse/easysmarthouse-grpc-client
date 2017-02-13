/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easysmarthouse.ui.webui.client.rpc;

import com.github.creepid.grpc.client.ServiceRelativePath;
import java.util.List;
import net.easysmarthouse.provider.device.trigger.Trigger;

/**
 *
 * @author rusakovich
 */
@ServiceRelativePath("trigger")
public interface TriggerService {

    public List<Trigger> getTriggers();

    public void setEnabled(String name, boolean enabled);

}
