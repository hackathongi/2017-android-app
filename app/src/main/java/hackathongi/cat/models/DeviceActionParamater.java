package hackathongi.cat.models;

import java.util.List;

/**
 * Created by pgarriga on 1/4/17.
 */

public class DeviceActionParamater {
    String name;
    String description;
    List<DeviceAction> paramaterList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
