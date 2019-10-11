package com.example.matthias.guizic;



import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by matthias on 2/23/18.
 */

public class MusicsManager {
    private List<MediaPlayer> mListMedia = new ArrayList<MediaPlayer>();
    private final double pasVolume = 100;
    private double maxVolume = 0;
    private double mVolume = 0;
    public int nbMediaPlayer = 0;

    public MusicsManager() {

    }

    //TODO : Normaliser et arguments des fonctions à modifier
    public void addMediaPlayer(MediaPlayer mediaPlayer) {
        mListMedia.add(mediaPlayer);
        maxVolume += pasVolume;
        nbMediaPlayer++;

    }

    public void addMediaPlayer(List<MediaPlayer> mediaPlayers) {
        mListMedia.addAll(mediaPlayers);
        nbMediaPlayer += mediaPlayers.size();
        maxVolume = pasVolume * nbMediaPlayer;
    }

    public void setVolume(double volume) {

        mVolume = volume;
        boolean init = false;

        for (MediaPlayer mediaPlayer : mListMedia) {
            if(mVolume < 0) {
                volume = 0;
                mediaPlayer.setVolume(0, 0);
                continue;
            }
            if (volume >= pasVolume) {
                mediaPlayer.setVolume(1, 1);
                volume -= pasVolume;
            } else if (!init) {
                final float offset = 5;
                volume += offset;
                float vol = (float) (1 - (Math.log(100 - volume / (1 + offset / 100)) / Math.log(100)));
                mediaPlayer.setVolume(vol, vol);
                volume = 0;
                init = true;
            } else {
                mediaPlayer.setVolume(0, 0);
            }
        }
    }

    public int getNBMediaPlayer() {
        return nbMediaPlayer;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public double getVolume() {
        return mVolume;
    }

    public void start() {
        for (MediaPlayer mediaPlayer : mListMedia) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public void stop() {
        for(MediaPlayer mediaPlayer : mListMedia) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        for(MediaPlayer mediaPlayer : mListMedia) {
            mediaPlayer.release();
        }
    }

    public void pause() {
        for(MediaPlayer mediaPlayer : mListMedia) {
            mediaPlayer.pause();
        }
    }

    public void prepare() {
        for(MediaPlayer mediaPlayer : mListMedia) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isPlaying() {
        if(mListMedia.size() == 0) {
            return false;
        }
        return mListMedia.get(0).isPlaying();
    }

}
