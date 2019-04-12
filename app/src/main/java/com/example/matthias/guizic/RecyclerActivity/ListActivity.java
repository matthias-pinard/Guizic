 package com.example.matthias.guizic.RecyclerActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
}
