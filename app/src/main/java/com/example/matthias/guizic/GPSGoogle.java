package com.example.matthias.guizic;

import android.content.Context;
import android.location.Location;

import com.example.matthias.guizic.Gps;
import com.example.matthias.guizic.GpsSimple;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GPSGoogle extends Gps {
    private Context mContext;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GpsChangeListener mGpsChangeListener;
    private boolean listenerIsActive = false;
    private float mDistanceDestination;
    private Location mDestination;

    public GPSGoogle(Context context, Location destination, GpsChangeListener gpsChangeListener) {
        mContext = context;
        mDestination = destination;
        mGpsChangeListener = gpsChangeListener;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mDistanceDestination = location.distanceTo(mDestination);
                }
                mGpsChangeListener.onCHangeDo();
            }
        };
    }

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
