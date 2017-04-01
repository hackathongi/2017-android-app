package hackathongi.cat.jati;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hackathongi.cat.models.Device;
import hackathongi.cat.models.DeviceAction;
import hackathongi.cat.tarla.TarlaService;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DetailsActivity extends AppCompatActivity {

    private Device mDevice;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDevice = (Device) getIntent().getSerializableExtra("Device");

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_details);

        mListView = (ListView) findViewById(R.id.list_view);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> actions = new ArrayList<String>();
        for(DeviceAction deviceAction : mDevice.getActionList()){
            actions.add(deviceAction.getName());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                actions );

        mListView.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                Log.d("PERE", mDevice.getActionList().get(position).getName());

                new DoActionTask(mDevice.getName(), mDevice.getActionList().get(position).getName()).execute();
            }
        });

        ((TextView) findViewById(R.id.name)).setText(mDevice.getName());
        ((TextView) findViewById(R.id.description)).setText(mDevice.getDescription());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    private class DoActionTask extends AsyncTask<Void, Void, Response> {

        private String device, action;
        private Exception exception = null;

        DoActionTask(String device, String action) {
            this.device = device;
            this.action = action;
        }

        @Override
        protected Response doInBackground(Void... params) {
            try {
                RestAdapter restAdapter = providesRestAdapter();

                TarlaService service = restAdapter.create(TarlaService.class);

                return service.action(device, action);

            } catch (Exception e) {
                this.exception = e;
                Log.e("Error", e.getLocalizedMessage());

                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
        }

        public RestAdapter providesRestAdapter() {
            //final OkHttpClient okHttpClient = new OkHttpClient();
            //okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
            //okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

            return new RestAdapter.Builder()
                    .setEndpoint("http://192.168.4.250/") // Endpoint
                    //.setClient(okHttpClient)
                    .setLogLevel( RestAdapter.LogLevel.FULL ) // Log level
                    .setLog(new AndroidLog("retrofit")) // Log
                    .build();
        }
    }

}
