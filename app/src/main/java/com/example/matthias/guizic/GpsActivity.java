package com.example.matthias.guizic;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GpsActivity extends AppCompatActivity {

    private final String TAG = "GPS_ACTIVITY_DEBUG";
    private Location mDestination;

    private Intent mIntent;

    private boolean mIsBound;

    private boolean mBoundState;

    private Gps mGps;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected (ComponentName className, IBinder binder) {
            MyService.LocalBinder localBinder = ((MyService.LocalBinder)binder).getService();
            mGps = localBinder.getGps();
            float longitude = mIntent.getFloatExtra("longitude", 0);
            float latitude = mIntent.getFloatExtra("latitude", 0);
            mDestination = new Location("User");
            mDestination.setLatitude(latitude);
            mDestination.setLongitude(longitude);


            mGps.setDestination(mDestination);
            TextView textViewDistance = (TextView) findViewById(R.id.textViewDistance);
            textViewDistance.setText(String.valueOf(mGps.getDistanceToDestination()));

            Log.d(TAG, "Distance : " + mGps.getDistanceToDestination());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mGps = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        mIntent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestLocationRigth();
        doBindService();
    }

    protected void onStop() {
        super.onStop();
        doUnbindService();
    }

    void doBindService() {
        Intent intent = new Intent(this, MyService.class);
        mIsBound = true;
        mBoundState = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d("Tag", "Service bound: " + mBoundState);
    }

    void doUnbindService () {
        Log.d("Tag", "Service bound: " + mBoundState);
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    void requestLocationRigth() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
    }

    public void onClickRefresh(View view) {
        float longitude = mIntent.getFloatExtra("longitude", 0);
        float latitude = mIntent.getFloatExtra("latitude", 0);
        mDestination = new Location("User");
        mDestination.setLatitude(latitude);
        mDestination.setLongitude(longitude);


        mGps.setDestination(mDestination);
        TextView textViewDistance = (TextView) findViewById(R.id.textViewDistance);
        textViewDistance.setText(String.valueOf(mGps.getDistanceToDestination()));

        Log.d(TAG, "Distance : " + mGps.getDistanceToDestination());
    }
}
