package com.example.matthias.guizic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class MyService extends Service {
    private static final String CHANNEL_ID = "GUIZIC";
    static private  String TAG = "DEBUG_SERVICE";
    private NotificationManager mNM;
    private LocalBinder mBinder = new LocalBinder();
    private Gps mGps;
    private boolean isArrived = false;

    private int NOTIFICATION = R.string.local_service_started;

    private MusicsManager mMusicsManager;

    private double mDistanceMaximum;
    int  mIsDistInit = 0;

    public class LocalBinder extends Binder {
        Gps getGps() {
            return mGps;
        }

        public void activateListener() {
            mGps.setListenerActive(true);
        }

        public void desactivateListener() {
            mGps.setListenerActive(false);
        }

        public void setGpsListener(Gps.GpsChangeListener gpsListener) {
            mGps.setGpsChangeListener(gpsListener);
        }
    }

    @Override
    public void onCreate() {
        promptForGps();
        mGps = new Gps(this);
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

    public void promptForGps() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

    // check if enabled and if not send user to the GSP settings
    // Better solution would be to display a dialog and suggesting to
    // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
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

    public void gpsListener() {
            double dist = mGps.getDistanceToDestination();
            double vol = mMusicsManager.getMaxVolume() - dist * 1.3;
            Log.d(TAG, "distance" + dist);
            Log.d(TAG, "Vol" + vol);
            //Log.d("Destination", mGps.getDestination().toString());
            mMusicsManager.setVolume( vol);
    }

    public void playWinMusic()
    {
        MediaPlayer musicWin = MediaPlayer.create(this, R.raw.no_hablo);
        mMusicsManager.setVolume(0);
        musicWin.start();
    }
    private Gps.GpsChangeListener mGpsChangeListener = new Gps.GpsChangeListener(){

        @Override
        public void onCHangeDo() {
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

}
