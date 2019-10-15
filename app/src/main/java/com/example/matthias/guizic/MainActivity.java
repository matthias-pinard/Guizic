package com.example.matthias.guizic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.matthias.guizic.AddZone.AddZone;
import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.RecyclerActivity.ListActivity;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add logo in Action Bar
        //---------------------------------------------------------
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //---------------------------------------------------------

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

    public void onClickAddSound(View view) {
        Intent intent = new Intent(this, AddSounds.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_like:

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=guizicnco.app.guizic&gl=FR"));
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
