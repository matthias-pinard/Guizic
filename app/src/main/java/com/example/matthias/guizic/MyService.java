package com.example.matthias.guizic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.Piste;
import com.example.matthias.guizic.Gps.GPSGoogle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private static final String CHANNEL_ID = "GUIZIC";
    static private  String TAG = "DEBUG_SERVICE";
    private NotificationManager mNM;
    private LocalBinder mBinder = new LocalBinder();
    private GPSGoogle mGps;
    private boolean isArrived = false;
    private GPSGoogle.GpsChangeListener mActivityListener;
    private Location mDestination;
    private int NOTIFICATION = R.string.local_service_started;
    private long mSoundId;
    private double mSensibilite = 1;
    private MusicsManager mMusicsManager;
    private OnFindListener mOnFindListener;

    private boolean mIsActivityListenerActive = false;

    public class LocalBinder extends Binder {
        GPSGoogle getGps() {
            return mGps;
        }
        MusicsManager getMusicManager() {
            return mMusicsManager;
        }
        public void activateListener() {
            mIsActivityListenerActive = true;
        }

        public void desactivateListener() {
           mIsActivityListenerActive = false;
        }

        public void setGpsListener(GPSGoogle.GpsChangeListener gpsListener) {
            mActivityListener = gpsListener;
        }
        public void setSoundId(long soundId) {
            mSoundId = soundId;
        }


        public void setSensibility(double sensibility) {
            mSensibilite = sensibility;
        }

        public void setDestination(Location location) {
            mDestination = location;
        }

        public double getSensibility() {
            return mSensibilite;
        }

        public void setOnFindListener(OnFindListener onFindListener) {mOnFindListener = onFindListener;}

        public void stopMusicManager() {
            if(mMusicsManager.isPlaying()) {
                mMusicsManager.stop();
            }
        }
    }

    @Override
    public void onCreate() {
        mGps = new GPSGoogle(this, mDestination, mGpsChangeListener);
        mGps.setListenerActive(true);


        Log.d("DEBUG", "Service bound.");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //Affiche une notification pour dire que le service à démarrer.
        showNotification();
        initMusic();
        mMusicsManager.setVolume(0);
        mMusicsManager.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        //On demande au système de relancer le service si il le détruit à cause d'um manque de mémoire
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // On annule la notification
        mGps.stopLocationUpdate();
        mNM.cancel(NOTIFICATION);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Affiche une notification
    private void showNotification() {
        createNotificationChannel();
        // On utilise le même texte en id de notification et en ticker.
        CharSequence text = getText(R.string.local_service_started);

        //Pending intent pour lancer l'activité si l'utilisateur clique sur la notification.
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setTicker(text);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(getText(R.string.local_service_label));
        builder.setContentText(text);
        builder.setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("GUIZIC");
        }
        Notification notification = builder.build();

        //send the notification
        mNM.notify(NOTIFICATION, notification);
    }

    public void initMusic() {
        List<MediaPlayer> mediasPlayers = new ArrayList<MediaPlayer>();
        Log.d("SOUND", "sound id: " + mSoundId);
        if(mSoundId == 0) {
            mediasPlayers.add(MediaPlayer.create(this, R.raw.stratte1));
            mediasPlayers.add(MediaPlayer.create(this, R.raw.stratte2));
            mediasPlayers.add(MediaPlayer.create(this, R.raw.stratte3));
            mediasPlayers.add(MediaPlayer.create(this, R.raw.stratte4));
            mediasPlayers.add(MediaPlayer.create(this, R.raw.stratte5));
        } else {
            List<Piste> lPistes = AppDatabase.getInstance(this).soundDao().getPistes(mSoundId);
            for(Piste piste : lPistes) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(piste.getPath());
                    mediasPlayers.add(mediaPlayer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        mMusicsManager = new MusicsManager();
        mMusicsManager.addMediaPlayer(mediasPlayers);
    }

    public void gpsListener() {
        double dist = mGps.getDistanceToDestination();
        double vol = mMusicsManager.getMaxVolume() - dist * mSensibilite;
        mMusicsManager.setVolume( vol);
        if(dist < 10 && !isArrived) {
            playWinMusic();
        }
        Log.d("DISTANCE", "d: " + dist + " maxVol: " + vol);

    }

    public void playWinMusic()
    {
        isArrived = true;
        MediaPlayer musicWin = MediaPlayer.create(this, R.raw.music_win);
        mMusicsManager.setVolume(0);
        mMusicsManager.stop();
        mGps.stopLocationUpdate();
        musicWin.start();
        musicWin.setOnCompletionListener(mp -> {
            mOnFindListener.onFind();
        });
    }

    private GPSGoogle.GpsChangeListener mGpsChangeListener = new GPSGoogle.GpsChangeListener(){

        @Override
        public void onCHangeDo() {
            if(mIsActivityListenerActive) {
                mActivityListener.onCHangeDo();
            }
            gpsListener();
        }
    };

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public interface OnFindListener {
        void onFind();
    }

}
