package com.example.matthias.guizic.AddZone;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.Database.Sound;
import com.example.matthias.guizic.MapPicker;
import com.example.matthias.guizic.R;

import java.util.List;

public class AddZone extends AppCompatActivity {

    public static final int REQUEST_CODE = 222;
    public static final int RESULT_OK = 333;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);
        setSpinner();
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

    public void onClickPickLocation(View view) {
        Intent intent = new Intent(this, MapPicker.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                TextView textViewLatitude = findViewById(R.id.inputLatitude);
                textViewLatitude.setText(String.valueOf(data.getDoubleExtra("latitude", 0)));
                TextView textViewLongitude = findViewById(R.id.inputLongitude);
                textViewLongitude.setText(String.valueOf(data.getDoubleExtra("longitude", 0)));
            }
        }
    }

    public void setSpinner() {
//        AppDatabase db = AppDatabase.getInstance(this);
//        Cursor cursor = db.soundDao().getCursor();
//        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.simple_spinner_item, null, R.id.spi)
//        Spinner spinner = findViewById(R.id.inputSounds);
//        spinner.setAdapter(cursorAdapter);
    }
}
