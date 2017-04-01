package hackathongi.cat.models;


import java.io.Serializable;

/**
 * Created by pgarriga on 1/4/17.
 */

public class DeviceActionParamater implements Serializable {
    String name;
    String description;

    public DeviceActionParamater(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
