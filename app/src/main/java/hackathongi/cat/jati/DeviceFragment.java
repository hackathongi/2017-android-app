package hackathongi.cat.jati;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hackathongi.cat.models.Device;
import hackathongi.cat.models.DeviceAction;
import hackathongi.cat.models.DeviceActionParamater;
import hackathongi.cat.tarla.TarlaService;
import okhttp3.Interceptor;

import okhttp3.Request;

import okhttp3.ResponseBody;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DeviceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    List<Device> mDeviceList = new ArrayList<Device>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DeviceFragment newInstance(int columnCount) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            // CODI D'EXEMPLE
            // CATAPULT
            DeviceActionParamater deviceCatapultAction1Parameter = new DeviceActionParamater("sec", "Delay until catapult shots");

            DeviceAction deviceCatapultAction1 = new DeviceAction("shot", "Shot the catapult");
            deviceCatapultAction1.addParameter(deviceCatapultAction1Parameter);

            Device deviceCatapult = new Device("catapult", "A catapult to kill intruders." );
            deviceCatapult.addAction(deviceCatapultAction1);

            mDeviceList.add(deviceCatapult);

            // WATERING
            DeviceAction deviceWateringAction1 = new DeviceAction("on", "Turn on");
            DeviceAction deviceWateringAction2 = new DeviceAction("off", "Turn off");
            DeviceAction deviceWateringAction3 = new DeviceAction("status", "Will return the humidity and temperature status");

            Device deviceWatering = new Device("watering", "Watering for plant maintenance." );
            deviceWatering.addAction(deviceWateringAction1);
            deviceWatering.addAction(deviceWateringAction2);
            deviceWatering.addAction(deviceWateringAction3);

            mDeviceList.add(deviceWatering);

            // CAMERA
            DeviceActionParamater deviceCameraAction4Parameter = new DeviceActionParamater("num", "For the pic cmd, the number of pictures to be returned (1 if not set)");

            DeviceAction deviceCameraAction1 = new DeviceAction("on", "Turn on");
            DeviceAction deviceCameraAction2 = new DeviceAction("off", "Turn off");
            DeviceAction deviceCameraAction3 = new DeviceAction("status", "Whether it is on or off");
            DeviceAction deviceCameraAction4 = new DeviceAction("pic", "Return last n pictures (1 by default)");
            deviceCameraAction4.addParameter(deviceCameraAction4Parameter);

            Device deviceCamera = new Device("camera", "Surveillance camera" );
            deviceCamera.addAction(deviceCameraAction1);
            deviceCamera.addAction(deviceCameraAction2);
            deviceCamera.addAction(deviceCameraAction3);
            deviceCamera.addAction(deviceCameraAction4);

            mDeviceList.add(deviceCamera);

            // BOMBETA
            DeviceActionParamater deviceBombetaAction1Parameter = new DeviceActionParamater("p1", "on / off");

            DeviceAction deviceBombetaAction1 = new DeviceAction("on", "Say a text using text-to-speech synthesis");
            deviceBombetaAction1.addParameter(deviceBombetaAction1Parameter);

            Device deviceBombeta = new Device("bombeta", "Llum de la casa" );
            deviceBombeta.addAction(deviceBombetaAction1);

            mDeviceList.add(deviceBombeta);

            // ANNOUNCER
            DeviceActionParamater deviceAnnouncerAction1Parameter = new DeviceActionParamater("what", "What to say");
            DeviceActionParamater deviceAnnouncerAction2Parameter = new DeviceActionParamater("voice", "Change voice/langauge. Default: spanish");
            DeviceActionParamater deviceAnnouncerAction3Parameter = new DeviceActionParamater("volume", "Change volume of sound. Default: 100");
            DeviceActionParamater deviceAnnouncerAction4Parameter = new DeviceActionParamater("speed", "Set speed, in words/minute. Default: 175");

            DeviceAction deviceAnnouncerAction1 = new DeviceAction("say", "Say a text using text-to-speech synthesis");
            deviceAnnouncerAction1.addParameter(deviceAnnouncerAction1Parameter);
            deviceAnnouncerAction1.addParameter(deviceAnnouncerAction2Parameter);
            deviceAnnouncerAction1.addParameter(deviceAnnouncerAction3Parameter);
            deviceAnnouncerAction1.addParameter(deviceAnnouncerAction4Parameter);

            Device deviceAnnouncer = new Device("announcer", "Announce things to people in the house" );
            deviceAnnouncer.addAction(deviceAnnouncerAction1);

            mDeviceList.add(deviceAnnouncer);


            // FACIAL
            DeviceAction deviceFacialAction1 = new DeviceAction("on", "Start the facial recognition process.");

            Device deviceFacial = new Device("facial", "Facial recognition" );
            deviceFacial.addAction(deviceFacialAction1);

            mDeviceList.add(deviceFacial);

            recyclerView.setAdapter(new MyDeviceRecyclerViewAdapter(mDeviceList, mListener));

            new AsyncTask<Void, Void, Response>() {

                private Exception exception = null;

                @Override
                protected Response doInBackground(Void... params) {
                    try {
                        RestAdapter restAdapter = new RestAdapter.Builder() // Builder
                                .setEndpoint("http://192.168.4.250/") // Endpoint
                                .setLogLevel( RestAdapter.LogLevel.FULL ) // Log level
                                .setLog(new AndroidLog("retrofit")) // Log
                                .build();

                        TarlaService service = restAdapter.create(TarlaService.class);

                        Response response = service.listDevices();;
                        return response;

                    } catch (Exception e) {
                        this.exception = e;
                        Log.e("Error", e.getLocalizedMessage());
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Response response) {
                    if (response != null) {

                        response.getBody();
                        //mDeviceList.addAll(deviceList);
                    }
                }
            }.execute();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Device item);
    }


}
