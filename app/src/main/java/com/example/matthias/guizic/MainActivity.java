package com.example.matthias.guizic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.matthias.guizic.RecyclerActivity.ListActivity;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Debug";

    SeekBar mSeekBar;

    MusicsManager mMusicsManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initMusic();
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMax(mMusicsManager.getNBMediaPlayer() * 100);
        Log.d(TAG, "Nb media player: " +mMusicsManager.getNBMediaPlayer());
        mSeekBar.setProgress(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekListener());
        mMusicsManager.setVolume(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicsManager.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMusicsManager.stop();
        mMusicsManager.prepare();
    }

    protected void onStart() {
        super.onStart();
        mMusicsManager.start();
    }

    public void seekBarOnProgressChange(SeekBar seekBar, int progress, boolean fromUser, MediaPlayer mediaPlayer) {
        mMusicsManager.setVolume(progress);
        Log.d(TAG, "Progress changed: " + progress);
    }

    public class SeekListener implements SeekBar.OnSeekBarChangeListener {

        MediaPlayer mMediaPlayer;
        public SeekListener() {
            Log.d(TAG, "Listener Initialized");
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            seekBarOnProgressChange(seekBar, i, b, mMediaPlayer);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }



    public void onClickTestGps(View view) {
        double longitude = Float.parseFloat(((EditText) findViewById(R.id.editTextLongitude)).getText().toString());
        double latitude  = Float.parseFloat(((EditText) findViewById(R.id.editTextLatitude)).getText().toString());
        Intent intent = new Intent(this, GpsActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    public void onClickListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void initMusic() {
        MediaPlayer bass = MediaPlayer.create(this, R.raw.bass);
        MediaPlayer drum = MediaPlayer.create(this, R.raw.drum);
        MediaPlayer pad = MediaPlayer.create(this, R.raw.pad);
        MediaPlayer synthLead = MediaPlayer.create(this, R.raw.synth_lead);

        mMusicsManager = new MusicsManager();
        mMusicsManager.addMediaPlayer(bass);
        mMusicsManager.addMediaPlayer(drum);
        mMusicsManager.addMediaPlayer(pad);
        mMusicsManager.addMediaPlayer(synthLead);
    }

    public void initSeekBar() {
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMax(mMusicsManager.getNBMediaPlayer() * 100);
        Log.d(TAG, "Nb media player: " +mMusicsManager.getNBMediaPlayer());
        mSeekBar.setProgress(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekListener());
    }
}
