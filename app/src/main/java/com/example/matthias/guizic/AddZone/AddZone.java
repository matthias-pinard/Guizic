package com.example.matthias.guizic.AddZone;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        String textName = textViewName.getText().toString();
        TextView textViewLatitude = findViewById(R.id.inputLatitude);
        String textLatitude = textViewLatitude.getText().toString();
        TextView textViewLongitude = findViewById(R.id.inputLongitude);
        String textLongitude = textViewLongitude.getText().toString();
        TextView textViewDistance = findViewById(R.id.inputDistance);
        String textDistance = textViewDistance.getText().toString();

        if(textName.equals("") || textLatitude.equals("") || textLongitude.equals("") || textDistance.equals("")) {
            CharSequence text = "Remplisser tout les champs avant  de confirmer.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            return;
        }


        String name = textViewName.getText().toString();

        double latitude = Double.parseDouble(textViewLatitude.getText().toString());

        double longitude = Double.parseDouble(textViewLongitude.getText().toString());

        double distance = Double.parseDouble(textViewDistance.getText().toString());

        double sensibilite = 500 / distance;

        Spinner spinner = findViewById(R.id.inputSounds);
        String soundName = spinner.getSelectedItem().toString();

        Log.d("SOUND", "sound name: " + soundName);
        AppDatabase db = AppDatabase.getInstance(this);
        long id = db.soundDao().getSoundId(soundName);

//        SecretZone secretZone = new SecretZone(longitude, latitude, sensibilite, name, id);
//        AppDatabase.getInstance(this).secretZoneDao().insertAll(secretZone);
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
                TextView textViewDistance = findViewById(R.id.inputDistance);
                textViewDistance.setText(String.valueOf(data.getDoubleExtra("distance", 0)));
            }
        }
    }

    public void setSpinner() {
        AppDatabase db = AppDatabase.getInstance(this);
        Cursor cursor = db.soundDao().getCursor();
        String[] from = {"name"};
        int[] to = {R.id.spinnerText};
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.simple_spinner_item, cursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Spinner spinner = findViewById(R.id.inputSounds);
        spinner.setAdapter(cursorAdapter);
    }
}
