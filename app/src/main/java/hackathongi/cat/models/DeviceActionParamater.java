package hackathongi.cat.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by pgarriga on 1/4/17.
 */

public class DeviceActionParamater {
    String description;


    @SerializedName("parameters")
    List<Map<String, DeviceActionParamater>> parameterList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
