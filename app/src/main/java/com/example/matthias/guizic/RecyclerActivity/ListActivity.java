 package com.example.matthias.guizic.RecyclerActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.R;
import com.example.matthias.guizic.utils.JsonReaderUtils;

import java.util.List;

 public class ListActivity extends AppCompatActivity {
     private RecyclerView mRecyclerView;
     private RecyclerView.Adapter mAdapter;
     private RecyclerView.LayoutManager mLayoutManager;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list);

         //Add logo in Action Bar
         //---------------------------------------------------------
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
         getSupportActionBar().setDisplayUseLogoEnabled(true);
         //---------------------------------------------------------

//         List<SecretZone> myDataset = AppDatabase.getInstance(this).secretZoneDao().getAll();
         JsonReaderUtils jsonReader = new JsonReaderUtils(this);
         List<SecretZone> myDataset =  jsonReader.parseJsonFileZone();

         mRecyclerView = findViewById(R.id.recycler_view);

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
