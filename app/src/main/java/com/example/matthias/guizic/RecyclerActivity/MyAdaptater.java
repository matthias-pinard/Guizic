package com.example.matthias.guizic.RecyclerActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.GpsActivity;
import com.example.matthias.guizic.R;

import java.util.List;

public class MyAdaptater extends RecyclerView.Adapter<MyAdaptater.MyViewHolder> {
    public List<SecretZone> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button mButton;
        public MyViewHolder(View v) {
            super(v);
            mButton = (Button) v.findViewById(R.id.button_recycler);
        }
    }

    public  MyAdaptater(List<SecretZone> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdaptater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // TODO use secret name
        final SecretZone secretZone = mDataset.get(position);
        final Context context = holder.mButton.getContext();
        String name = secretZone.getSecretName();
        holder.mButton.setText(name);
        holder.mButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, GpsActivity.class);
            intent.putExtra("latitude", secretZone.getLatitude());
            intent.putExtra("longitude", secretZone.getLongitude());
            intent.putExtra("sensibilite", secretZone.getSensibilite());
            intent.putExtra("name", secretZone.getSecretName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
