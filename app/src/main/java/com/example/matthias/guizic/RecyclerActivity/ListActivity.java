 package com.example.matthias.guizic.RecyclerActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.matthias.guizic.AddZone;
import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.R;

import java.util.List;

 public class ListActivity extends AppCompatActivity {
     private RecyclerView mRecyclerView;
     private RecyclerView.Adapter mAdapter;
     private RecyclerView.LayoutManager mLayoutManager;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list);

         List<SecretZone> myDataset = AppDatabase.getInstance(this).secretZoneDao().getAll();

         mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

         // use this setting to improve performance if you know that changes
         // in content do not change the layout size of the RecyclerView
         mRecyclerView.setHasFixedSize(true);

         // use a linear layout manager
         mLayoutManager = new LinearLayoutManager(this);
         mRecyclerView.setLayoutManager(mLayoutManager);

         // specify an adapter (see also next example)
         mAdapter = new MyAdaptater(myDataset);
         mRecyclerView.setAdapter(mAdapter);
     }

     protected  void onClickAdd(View view) {
         Intent intent = new Intent(this, AddZone.class);
         startActivity(intent);
     }
/*     public static String TAG = "DEBUGDB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final Context context = this;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(context);
                SecretZone secretZone = new SecretZone(-1.636408, 48.118096, 1.3, "Esir", "E");
                db.secretZoneDao().insertAll(secretZone);
                List<SecretZone> secretZones = db.secretZoneDao().getAll();
                Log.d(TAG, String.valueOf(secretZones.size()));
                final String name = secretZones.get(0).getRealName();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button button = findViewById(R.id.button2);
                        button.setText(name);
                    }
                });
            }
        });

    }*/
}
