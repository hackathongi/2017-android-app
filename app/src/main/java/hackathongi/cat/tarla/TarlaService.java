package hackathongi.cat.tarla;

import java.util.List;
import java.util.Map;

import hackathongi.cat.models.Device;
import retrofit.client.Response;
import retrofit.http.GET;


/**
 * Created by pgarriga on 1/4/17.
 */
public interface TarlaService {
    @GET("/devices")
    Response listDevices();
}
