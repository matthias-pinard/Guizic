package com.example.matthias.guizic.AddZone;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.matthias.guizic.R;

public class CursorAdaptaterSound extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public CursorAdaptaterSound(Context context, Cursor c) {
        super(context, c, 0);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.my_text_view, viewGroup, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewSpinner = view.findViewById(R.id.spinnerText);
        String soundTitle = cursor.getString(cursor.getColumnIndex("name"));
        textViewSpinner.setText(soundTitle);
    }
}
