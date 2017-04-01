package hackathongi.cat.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgarriga on 1/4/17.
 */

public class Device implements Serializable{
    String name;
    String description;
    List<DeviceAction> actionList;

    public Device(String name, String description) {
        this.name = name;
        this.description = description;

        this.actionList = new ArrayList<>();
    }

    public void addAction(DeviceAction deviceAction){
        this.actionList.add(deviceAction);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<DeviceAction> getActionList() {
        return actionList;
    }
}
