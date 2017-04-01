package hackathongi.cat.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by pgarriga on 1/4/17.
 */

public class Device {
    String name;
    String description;
    List<DeviceAction> actionList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
