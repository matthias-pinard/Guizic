package com.example.matthias.guizic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.matthias.guizic.AddZone.AddZone;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPicker extends AppCompatActivity implements OnMapReadyCallback {

    LatLng mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng));
            mPosition = latLng;
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
        setResult(AddZone.RESULT_OK,  intent);
        finish();
    }
}
