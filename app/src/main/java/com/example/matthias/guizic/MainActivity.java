package com.example.matthias.guizic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Debug";

    SeekBar seekBarNoHablo;
    SeekBar seekBarBass;
    SeekBar seekBarDrum;
    SeekBar seekBarSynthLead;

    MediaPlayer noHablo;
    MediaPlayer bass;
    MediaPlayer drum;
    MediaPlayer pad;
    MediaPlayer synthLead;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noHablo = MediaPlayer.create(this, R.raw.no_hablo);

        bass = MediaPlayer.create(this, R.raw.bass);
        bass.setLooping(true);

        drum = MediaPlayer.create(this, R.raw.drum);
        drum.setLooping(true);

        pad = MediaPlayer.create(this, R.raw.pad);
        pad.setLooping(true);

        synthLead = MediaPlayer.create(this, R.raw.synth_lead);
        synthLead.setLooping(true);

//         seekBarNoHablo = findViewById(R.id.seekBarNoHablo);
//         seekBarNoHablo.setOnSeekBarChangeListener(new SeekListener(noHablo));
//
//         seekBarBass = findViewById(R.id.seekBarBass);
//         seekBarBass.setOnSeekBarChangeListener(new SeekListener(bass));
//
//         seekBarDrum = findViewById(R.id.seekBarDrum);
//         seekBarDrum.setOnSeekBarChangeListener(new SeekListener(drum));
//
//         seekBarSynthLead = findViewById(R.id.seekBarSynthLead);
//         seekBarSynthLead.setOnSeekBarChangeListener(new SeekListener(synthLead));

        Log.d(TAG, "App created");
    }

    public void onClickPlaySound(View view) {

        noHablo.start();
        bass.start();
        drum.start();
        synthLead.start();
    }

    public void seekBarOnProgressChange(SeekBar seekBar, int progress, boolean fromUser, MediaPlayer mediaPlayer) {
        float maxVolume = 100;
        float log1=(float)(Math.log(maxVolume-progress)/Math.log(maxVolume));
        mediaPlayer.setVolume(1 - log1, 1 - log1);
    }

    public class SeekListener implements SeekBar.OnSeekBarChangeListener {

        MediaPlayer mMediaPlayer;
        public SeekListener(MediaPlayer mediaPlayer) {
            mMediaPlayer = mediaPlayer;
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
        float longitude = Float.parseFloat(((EditText) findViewById(R.id.editTextLongitude)).getText().toString());
        float latitude  = Float.parseFloat(((EditText) findViewById(R.id.editTextLatitude)).getText().toString());
        Intent intent = new Intent(this, GpsActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }
}
