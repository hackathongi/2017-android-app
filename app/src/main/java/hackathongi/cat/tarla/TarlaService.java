package hackathongi.cat.tarla;

import java.util.List;
import java.util.Map;

import hackathongi.cat.models.Device;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by pgarriga on 1/4/17.
 */
public interface TarlaService {
    @GET("/devices")
    Response listDevices();

    @GET("/devices/{device}/cmds/{action}")
    Response action(@Path("device") String device, @Path("action") String action);
}
