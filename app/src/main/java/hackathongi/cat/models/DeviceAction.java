package hackathongi.cat.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgarriga on 1/4/17.
 */

public class DeviceAction {
    String name;
    String description;

    List<DeviceActionParamater> parameterList;

    public DeviceAction(String name, String description) {
        this.name = name;
        this.description = description;

        this.parameterList = new ArrayList<>();
    }
}
