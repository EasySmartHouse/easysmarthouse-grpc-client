# EasySmartHouse services client
Light client based on GWT-RPC binary protocol that may be used to connect to EasySmartHouse services from any thin client or Android application.

The goals of the project are the study of GWT-RPC binary protocol and writing a light client to use it.

# Usage
### Service definition ###
```java
import by.ginger.smarthome.provider.device.actuator.Actuator;
import com.github.creepid.grpc.client.ServiceRelativePath;
import java.util.List;

@ServiceRelativePath("actuators")
public interface ActuatorsService {

    public List<Actuator> getActuators();

    public void changeState(String address, Boolean state);

    public void changeState(String address, Double state);

}
```

### Service client examples ###
```java
GRpcSettings settings = new GRpcSettings();
//define base services url
settings.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
//set GWT policy filename
settings.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "716B355B2C588BA58EC6DD661C6990FB");

//create the service 
ActuatorsService service = (ActuatorsService) GRPC.create(ActuatorsService.class, settings);

//use the service
List<Actuator> actuators = service.getActuators();
...

``` 