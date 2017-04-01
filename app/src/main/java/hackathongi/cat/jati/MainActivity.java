package hackathongi.cat.jati;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import hackathongi.cat.models.Device;

public class MainActivity extends FragmentActivity implements DeviceFragment.OnListFragmentInteractionListener,
        VoiceFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    DeviceFragment deviceFragment = new DeviceFragment();

                    // add fragment to the fragment container layout
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, deviceFragment).commit();
                    return true;

                case R.id.navigation_dashboard:
                    VoiceFragment voiceFragment = new VoiceFragment();

                    // add fragment to the fragment container layout
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, voiceFragment).commit();
                    return true;

                case R.id.navigation_notifications:
                    SettingsFragment settingsFragment = new SettingsFragment();

                    // add fragment to the fragment container layout
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, settingsFragment).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.content) != null) {
            // Create an instance of editorFrag
            DeviceFragment deviceFragment = new DeviceFragment();

            // add fragment to the fragment container layout
            getSupportFragmentManager().beginTransaction().add(R.id.content, deviceFragment).commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Device item) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("Device", item);
        startActivity(i);

        Log.d("LOG", item.getName() + " | " + item.getDescription());
    }
}
