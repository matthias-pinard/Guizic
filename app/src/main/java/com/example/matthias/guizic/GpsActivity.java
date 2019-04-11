package com.example.matthias.guizic;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.matthias.guizic.Gps.GPSGoogle;

public class GpsActivity extends AppCompatActivity {

    private final String TAG = "GPS_ACTIVITY_DEBUG";
    private Location mDestination;
    private boolean mIsDestInit = false;
    private Intent mIntent;
    private double mSensibilite = 0;


    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private boolean mIsBound;

    private boolean mBoundState;

    private GPSGoogle mGps;
    private MusicsManager mMusicsManager;

    private String mName;

    private GPSGoogle.GpsChangeListener mGpsChangeListener = () -> {
        refresh();
        Log.d("GPSCHANGE", "Position update");
    };

    MyService.LocalBinder localBinder;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected (ComponentName className, IBinder binder) {
            localBinder = (MyService.LocalBinder) binder;
            mGps = localBinder.getGps();
            mMusicsManager = localBinder.getMusicManager();
            localBinder.setGpsListener(mGpsChangeListener);
            localBinder.setSensibility(mSensibilite);
            localBinder.setDestination(mDestination);
            localBinder.activateListener();
            mGps.setDestination(mDestination);

            Log.d(TAG, "Distance : " + mGps.getDistanceToDestination());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            localBinder.desactivateListener();
            mGps = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mDestination = new Location("User");
        mIntent = getIntent();
        Uri data = mIntent.getData();
        if(data != null && data.isHierarchical())
        {
            if(data.getQueryParameter("latitude") != null &&
                    data.getQueryParameter("longitude") != null)
            {
                double longitude = Double.parseDouble(data.getQueryParameter("latitude"));
                double latitude = Double.parseDouble(data.getQueryParameter("longitude"));
                mDestination.setLongitude(longitude);
                mDestination.setLatitude(latitude);
                mIsDestInit = true;
            }
        }
        else {
            double longitude = mIntent.getDoubleExtra("longitude", -1);
            double latitude = mIntent.getDoubleExtra("latitude", -1);
            mName = mIntent.getStringExtra("name");
            mSensibilite = mIntent.getDoubleExtra("sensibilite", 1);
            Log.d(TAG, "Latitude : " + latitude + ", longitude : " + longitude);
            mDestination.setLatitude(latitude);
            mDestination.setLongitude(longitude);
            mIsDestInit = true;
        }

        requestPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
        mMusicsManager.stop();
        mMusicsManager.release();
    }

    void doBindService() {
        Intent intent = new Intent(this, MyService.class);
        mIsBound = true;
        mBoundState = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "Service bound: " + mBoundState);
    }

    void doUnbindService () {
        Log.d(TAG, "Service bound: " + mBoundState);
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    public void refresh() {
        int nbBoucle = (int)(mMusicsManager.getVolume() / 100) + 1;
        int max = (int) (mMusicsManager.getMaxVolume() / 100) + 1;
        String str = mName ;
        str += "\n\n" + nbBoucle + "/" + (max - 1);
//        String distance = String.format("%.2f", mGps.getDistanceToDestination());
//        str += "\nDist: " + distance + "m";
//        str += "\nVol: " + (int)mMusicsManager.getVolume();
        str += "\nSens: " + mSensibilite;
        TextView textViewDistance = (TextView) findViewById(R.id.textViewDestination);
        textViewDistance.setText(String.valueOf(str));

//        TextView textViewDistance = (TextView) findViewById(R.id.textViewDestination);
//        textViewDistance.setText(String.valueOf(distance));
//
//        Log.d(TAG, "Distance : " + mGps.getDistanceToDestination());
    }

    public boolean requestPermission() {
        final Activity activity = this;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        } else {
            doBindService();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION :{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    doBindService();
                } else {
                    finish();
                }
            }
        }
    }
}
