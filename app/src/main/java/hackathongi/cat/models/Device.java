package hackathongi.cat.models;

import java.util.List;

/**
 * Created by pgarriga on 1/4/17.
 */

public class Device {
    String name;
    String description;
    List<DeviceAction> actionList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
