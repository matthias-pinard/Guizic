package com.example.matthias.guizic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.example.matthias.guizic.Gps;
import com.example.matthias.guizic.GpsSimple;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPSGoogle {
    private Context mContext;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GpsChangeListener mGpsChangeListener;
    private boolean listenerIsActive = false;
    private float mDistanceDestination;
    private Location mDestination;
    LocationCallback mLocationCallback;

    public GPSGoogle(Context context, Location destination, GpsChangeListener gpsChangeListener) {
        mContext = context;
        mDestination = destination;
        mGpsChangeListener = gpsChangeListener;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getLastLocation();
        startLocationUpdate();
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mDistanceDestination = location.distanceTo(mDestination);
                }
                if(listenerIsActive) {
                    try {
                        mGpsChangeListener.onCHangeDo();
                    } catch (Exception e){

                    }
                }
            }
        };
        mFusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), mLocationCallback, null);
    }

    public void stopLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }
    public void setListenerActive(boolean value) {
        listenerIsActive = value;
    }

    public void setDestination(Location destination) {
        mDestination = destination;
    }

    public double getDistanceToDestination() {
        return mDistanceDestination;
    }
    public interface GpsChangeListener {
        void onCHangeDo();
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener( location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null && mDestination != null) {
                        mDistanceDestination = location.distanceTo(mDestination);
                        if(listenerIsActive) {
                            mGpsChangeListener.onCHangeDo();
                        }
                    }
                });
    }
}
