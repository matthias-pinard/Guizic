package com.example.matthias.guizic;



import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by matthias on 2/23/18.
 */

public class MusicsManager {
    private List<MediaPlayer> mListMedia = new ArrayList<MediaPlayer>();
    private final float pasVolume = 100;
    private float maxVolume = 0;
    private float mVolume = 0;
    public int nbMediaPlayer = 0;

    public MusicsManager() {

    }

    public void addMediaPlayer(MediaPlayer mediaPlayer) {
        mListMedia.add(mediaPlayer);
        maxVolume += pasVolume;
        nbMediaPlayer++;

    }

    public void setVolume(float volume) {

        Log.d("Volume", "" + volume);
        for(MediaPlayer mediaPlayer : mListMedia)
        {
            boolean init = false;
            if(volume >= pasVolume)
            {
                mediaPlayer.setVolume(1,1);
                volume -= pasVolume;
            }

            else if (!init)
            {
                float vol=(float) (1 - (Math.log(100 - volume) / Math.log(100)));
                mediaPlayer.setVolume(vol,vol);
                volume = 0;
                init = true;
            }
            else {
                mediaPlayer.setVolume(0,0);
            }
        }
    }

    public int getNBMediaPlayer()
    {
        return nbMediaPlayer;
    }

    public void start()
    {
        for(MediaPlayer mediaPlayer : mListMedia)
        {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }
}
