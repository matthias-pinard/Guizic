package com.example.matthias.guizic;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matthias.guizic.RecyclerActivity.ListActivity;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        String imgIdName = intent.getStringExtra("image");
        int imgId = getResources().getIdentifier(imgIdName, "drawable", getPackageName());
        String info = intent.getStringExtra("info");

        ImageView imageView = findViewById(R.id.imageViewPoint);
        TextView infoText = findViewById(R.id.textInfo);

        imageView.setImageResource(imgId);
        infoText.setText(info);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
