package com.example.kabhishek1.custom_listplusfetchv0;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by k.abhishek1 on 19-04-2017.
 */


public class BackgroundSoundService extends Service  {
    static MediaPlayer player;
    public static final String TAG = "12345";
    public static int length =0;
    int flag = 0;
    public BackgroundSoundService() {

    }


    public void onCreate()
    {
        super.onCreate();

//        final Intent intent =getI
//        final String desi = intent.getStringExtra("PATH");
        Log.d(TAG,"begin notification");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent != null) {





            Bundle extras = intent.getExtras();
            String userID = extras.getString("Path");

            String str = userID.toString();
            Log.d(TAG,str);



            //for playing only one song at a time





            // release the resources if the player is already running
            if(player == null) {

                player = new MediaPlayer();
                Log.d(TAG,"player ref created");

            }
            else {
                if(player.isPlaying())
                    player.release();


                player = new MediaPlayer();
                Log.d(TAG, "player ref released");

            }

//                Log.d(TAG,player.getCurrentPosition());



            //run a media player if a new song is to be played

            try {
                player.setDataSource(userID);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Log.d(TAG,"b4 prepare");

            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Log.d(TAG,"b4");






//        player.seekTo(length);
            player.start();
            Log.d(TAG,"player started");


//            Log.d(TAG,"after");


        }




        return START_STICKY;
    }




    public static MediaPlayer curInstance(){

        return player;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"inside the destroy ");
        super.onDestroy();
        player.pause();
        length =player.getCurrentPosition();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    public  static void onStop(){

        player.pause();
        Log.d(TAG,"inside the pause ");
        length = player.getCurrentPosition();

    }
    public static boolean playSong(){
        player.start();


        return false;
    }


    public static   void onResume(){

        player.seekTo(length);
        player.start();

    }


    public static void nextSongb4() {

        player.release();
    }
}


