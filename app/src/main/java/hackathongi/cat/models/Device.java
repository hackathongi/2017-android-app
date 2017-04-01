package hackathongi.cat.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pgarriga on 1/4/17.
 */

public class Device {
    String name;
    String description;
    List<DeviceAction> actionList;

    public Device(String name, String description) {
        this.name = name;
        this.description = description;

        this.actionList = new ArrayList<>();
    }
}
