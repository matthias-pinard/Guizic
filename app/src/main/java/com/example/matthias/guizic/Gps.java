package com.example.matthias.guizic;

import android.content.Context;
import android.location.Location;

public abstract class Gps {
    private final String TAG = "GPS_DEBUG";

    public Context getContext() {
        return mContext;
    }

    private Context mContext;

    private Location mDestination;
    private float mDistanceDestination;
    private GpsChangeListener mGpsChangeListener;
    private boolean listenerIsActive = false;


    public void setDestination(Location destination) {
        mDestination = destination;
    }

    public double getDistanceToDestination() {
        return mDistanceDestination;
    }

    public Location getDestination() {
        return mDestination;
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
