package com.example.matthias.guizic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.R;

public class AddZone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);
    }

    public void onCLick(View view) {
        TextView textViewName = findViewById(R.id.inputName);
        String name = textViewName.getText().toString();

        TextView textViewLatitude = findViewById(R.id.inputLatitude);
        double latitude = Double.parseDouble(textViewLatitude.getText().toString());

        TextView textViewLongitude = findViewById(R.id.inputLongitude);
        double longitude = Double.parseDouble(textViewLongitude.getText().toString());

        TextView textViewDistance = findViewById(R.id.inputDistance);
        double distance = Double.parseDouble(textViewDistance.getText().toString());

        double sensibilite = 500 / distance;
        SecretZone secretZone = new SecretZone(longitude, latitude, sensibilite, name, name);
        AppDatabase.getInstance(this).secretZoneDao().insertAll(secretZone);
        finish();
    }
}
