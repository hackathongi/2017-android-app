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

            DeviceActionParamater deviceActionParamater = new DeviceActionParamater("sec", "Delay until catapult shots");

            DeviceAction deviceAction = new DeviceAction("shot", "Shot the catapult");
            deviceAction.addParameter(deviceActionParamater);

            Device device = new Device("Catapult", "A catapult to kill intruders." );
            device.addAction(deviceAction);

            mDeviceList.add(device);

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
