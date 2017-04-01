package hackathongi.cat.jati;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

import hackathongi.cat.models.Device;
import hackathongi.cat.models.DeviceAction;

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
            }
        });

        ((TextView) findViewById(R.id.name)).setText(mDevice.getName());
        ((TextView) findViewById(R.id.description)).setText(mDevice.getDescription());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
