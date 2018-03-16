package com.example.matthias.guizic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


/**
 * Created by matthias on 2/23/18.
 */

public class Gps {

    private final String TAG = "GPS_DEBUG";
    private Context mContext;

    private Location mDestination;
    private float mDistanceDestination;
    private GpsChangeListener mGpsChangeListener;
    private boolean listenerIsActive = false;

    public Gps(Context context) {
        mContext = context;

        LocationManager locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    public void setDestination(Location destination) {
        mDestination = destination;
    }

    public double getDistanceToDestination() {
        return mDistanceDestination;
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mDistanceDestination = mDestination.distanceTo(location);
            if(listenerIsActive) {
                mGpsChangeListener.onCHangeDo();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void setListenerActive(boolean value) {
        listenerIsActive = value;
    }

    public void setGpsChangeListener(GpsChangeListener gpsChangeListener) {
        mGpsChangeListener = gpsChangeListener;
    }
    public interface GpsChangeListener {
        void onCHangeDo();
    }
}
