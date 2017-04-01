package hackathongi.cat.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgarriga on 1/4/17.
 */

public class DeviceAction implements Serializable {
    String name;
    String description;

    List<DeviceActionParamater> parameterList;

    public DeviceAction(String name, String description) {
        this.name = name;
        this.description = description;

        this.parameterList = new ArrayList<>();
    }

    public void addParameter(DeviceActionParamater deviceActionParamater){
        this.parameterList.add(deviceActionParamater);
    }

    public String getName() {
        return name;
    }

}
