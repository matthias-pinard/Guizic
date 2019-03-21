package com.example.matthias.guizic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.RecyclerActivity.ListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase.getInstance(this).secretZoneDao().getAll();
    }

    
    public void onClickListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void onClickResetDatabase(View view) {
        AppDatabase.getInstance(this).secretZoneDao().deleteAll();
        AppDatabase.loadJson(this);
    }

    public void onClickAddZone(View view) {
        Intent intent = new Intent(this, AddZone.class);
        startActivity(intent);
    }
}
