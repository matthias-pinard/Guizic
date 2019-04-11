package com.example.matthias.guizic;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.Piste;
import com.example.matthias.guizic.Database.Sound;

import java.util.ArrayList;
import java.util.List;

public class AddSounds extends AppCompatActivity {

    private final int READ_REQUEST_CODE = 42;
    private List<Piste> lPistes;
    private int sound_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sounds);
        lPistes = new ArrayList<>();
        sound_id = getSoundId();

    }

    public void onClickSave(View view) {
        AppDatabase db = AppDatabase.getInstance(this);
        Piste[] pistes = lPistes.toArray(new Piste[0]);
        db.pisteDao().insert(pistes);

        EditText editText = findViewById(R.id.editText);
        String soundName = editText.getText().toString();

        Sound sound= new Sound(soundName);
        db.soundDao().insert(sound);
        finish();

    }

    public int getSoundId() {
        AppDatabase db = AppDatabase.getInstance(this);
        return db.soundDao().getMaxId();
    }

    public void onClickAddSound(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Toast.makeText(this, "Uri:" + uri.toString(), Toast.LENGTH_LONG).show();
            }
            String path = uri.getPath();
            String name = queryName(uri);
            Piste piste = new Piste(name, path, sound_id);

            TextView textView = findViewById(R.id.textSounds);
            textView.setText(textView.getText() + "\n" + name);
            lPistes.add(piste);
        }
    }

    private String queryName(Uri uri) {
        ContentResolver resolver = getContentResolver();
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
}
