package com.example.matthias.guizic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.example.matthias.guizic.AddZone.AddZone;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPicker extends AppCompatActivity implements OnMapReadyCallback {

    LatLng mPosition;
    Circle mCircle;
    double mCircleRadius = 400.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SeekBar seekBar = findViewById(R.id.seekBarMap);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCircleRadius = (double)i;
                mCircle.setRadius((double) i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng));
            mPosition = latLng;

            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(mPosition)
                    .radius(mCircleRadius) // In meters
                    .fillColor(Color.argb(50, 255, 0, 0));

            // Get back the mutable Circle
            mCircle = googleMap.addCircle(circleOptions);
        });
        updateMapPosition(googleMap);

    }

    @SuppressLint("MissingPermission")
    public void updateMapPosition(GoogleMap map) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 16);
                map.moveCamera(cameraUpdate);
            }
        });

    }

    public void onClick(View view) {
        Intent intent = new Intent();
        Log.d("DDDBUG", "" + mPosition.longitude);
        intent.putExtra("latitude", mPosition.latitude);
        intent.putExtra("longitude", mPosition.longitude);
        intent.putExtra("distance", mCircle.getRadius());
        setResult(AddZone.RESULT_OK, intent);
        finish();
    }
}
