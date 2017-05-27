package com.example.kabhishek1.musicv1;

/**
 * Created by kumar abhi on 5/20/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by k.abhishek1 on 19-04-2017.
 */


public class Player {
    public  static MediaPlayer player;
    public static final String TAG = "12345";
    public static int length =0;
    int flag = 0;
    String userID;
    private static Player sInstance;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    public boolean mAudioIsPlaying = false;

    private boolean mAudioFocusGranted = false;
    private AudioManager audioManager;
    public static BroadcastReceiver mIntentReceiver;
    private static String SERVICE_CMD = "com.sec.android.app.music.musicservicecommand";
    private static String PAUSE_SERVICE_CMD = "com.sec.android.app.music.musicservicecommand.pause";
    private static String PLAY_SERVICE_CMD = "com.sec.android.app.music.musicservicecommand.play";

    private static final String CMD_NAME = "command";
    private static final String CMD_PAUSE = "pause";
    private static final String CMD_STOP = "pause";
    private static final String CMD_PLAY = "play";
    private boolean mReceiverRegistered = false;
    private int position;

    private static AudioModel audioModel;






    public static Player getInstance(Context context, int pos) {
        if (sInstance == null) {
            sInstance = new Player(context,pos);
        }
        return sInstance;
    }

    public Context mContext;
//    public void onCreate(Bundle bundle) {
//    {
//        super.onCreate(bundle);
//
////        final Intent intent =getI
////        final String desi = intent.getStringExtra("PATH");
//        Log.d(TAG,"begin notification");
//        setupBroadcastReceiver();
//
//
//    }



    public Player(Context context,int pos) {
        mContext = context;
        this.position =Integer.valueOf( MainActivity2.pos);












        Log.d(TAG,"constructor called "+ position+ musicTab1.data.pathM.get(position));
//        setupBroadcastReceiver();
//        Intent i = getIntent();
//        System.out.println("12345"+i.getStringExtra(CMD_NAME));

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        Log.d(TAG, "AUDIOFOCUS_GAIN");
                        play();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        Log.d(TAG, "AUDIOFOCUS_GAIN_TRANSIENT");
//                        pause();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        Log.i(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK");
//                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        Log.d(TAG, "AUDIOFOCUS_LOSS");
                        pause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                        pause();
//                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
//                        pause();
                        break;
                    case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                        Log.e(TAG, "AUDIOFOCUS_REQUEST_FAILED");
//                        pause();
                        break;
                    default:
                        //
                }
            }
        };


    }



    public void play() {
        System.out.println("12345 inside the play mAudioIsplaying  "+mAudioIsPlaying);
        if (!mAudioIsPlaying) {
            if (player == null) {
                Log.d(TAG,"insideeeeee-------");
                player = new MediaPlayer();


                Log.d(TAG, " mIntentReceiver.onReceive " );









                try {
                    player.setDataSource(musicTab1.data.pathM.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            Log.d(TAG,"b4 prepare");

//                try {
//                    player.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                player = MediaPlayer.create(mContext, R.raw.krispy_mixdown);
                player.setLooping(true);
            }


            System.out.println("12345 audiofocusgranted  "+mAudioFocusGranted );


            // 1. Acquire audio focus
            if (!mAudioFocusGranted && requestAudioFocus()) {
                // 2. Kill off any other play back sources
                forceMusicStop();
                // 3. Register broadcast receiver for player intents

                setupBroadcastReceiver();
                System.out.println("12345 inside");
//                    player.start();
//                    mAudioIsPlaying = true;


            }
            player.start();
            mAudioIsPlaying = true;
            // 4. Play music

        }
    }



    public void playp(String path ) {
        System.out.println("clicked on item inside the play mAudioIsplaying  "+path);
        if (!mAudioIsPlaying) {
            if (player == null) {
                Log.d(TAG,"insideeeeee-------");
                player = new MediaPlayer();
                player.reset();


                Log.d(TAG, " mIntentReceiver.onReceive " );









                try {
                    player.setDataSource(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            Log.d(TAG,"b4 prepare");
//
                try {
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                player = MediaPlayer.create(mContext, R.raw.krispy_mixdown);
                player.setLooping(true);
            }


            System.out.println("12345 audiofocusgranted  "+mAudioFocusGranted );


            // 1. Acquire audio focus
            if (!mAudioFocusGranted && requestAudioFocus()) {
                // 2. Kill off any other play back sources
                forceMusicStop();
                // 3. Register broadcast receiver for player intents

                setupBroadcastReceiver();
                System.out.println("12345 inside");
//                    player.start();
//                    mAudioIsPlaying = true;


            }



//            player.prepareAsync();
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    // Do something. For example: playButton.setEnabled(true);
//                    player.start();
//                }
//            });


            player.start();
            mAudioIsPlaying = true;
            // 4. Play music

        }
    }



    public void stop() {
        // 1. Stop play back
        if (mAudioFocusGranted && mAudioIsPlaying) {
//            player.stop();
            player.release();
            player = null;
            mAudioIsPlaying = false;
            // 2. Give up audio focus
            abandonAudioFocus();
        }
    }

    public void pause() {
        // 1. Suspend play back
        if (mAudioFocusGranted && mAudioIsPlaying) {
            player.pause();
            mAudioIsPlaying = false;
            abandonAudioFocus();
        }
    }

    private boolean requestAudioFocus() {
        if (!mAudioFocusGranted) {
            AudioManager am = (AudioManager) mContext
                    .getSystemService(Context.AUDIO_SERVICE);
            // Request audio focus for play back
            int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                    // Use the music stream.
                    AudioManager.STREAM_MUSIC,
                    // Request permanent focus.
                    AudioManager.AUDIOFOCUS_GAIN);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mAudioFocusGranted = true;
            } else {
                // FAILED
                Log.e(TAG,
                        ">>>>>>>>>>>>> FAILED TO GET AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }

        System.out.println("12345 mAuodioFocusgranted "+mAudioFocusGranted);
        return mAudioFocusGranted;
    }


    private void abandonAudioFocus() {
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        int result = am.abandonAudioFocus(mOnAudioFocusChangeListener);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mAudioFocusGranted = false;
        } else {
            // FAILED
            Log.e(TAG,
                    ">>>>>>>>>>>>> FAILED TO ABANDON AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
        }
//        mOnAudioFocusChangeListener = null;
    }


    private void forceMusicStop() {
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        if (am.isMusicActive()) {
            Intent intentToStop = new Intent(SERVICE_CMD);
            intentToStop.putExtra(CMD_NAME, CMD_STOP);
            mContext.sendBroadcast(intentToStop);
            System.out.println("12345  "+"command send to stop all music player");
        }
    }



    private void setupBroadcastReceiver() {


        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"55555500 onreceive");
//                String action = intent.getAction();
//                String cmd = intent.getStringExtra(CMD_NAME);
//                Log.i(TAG, "mIntentReceiver.onReceive " + action + " / " + cmd);

//                if (PAUSE_SERVICE_CMD.equals(action)
//                        || (SERVICE_CMD.equals(action) && CMD_PAUSE.equals(cmd))) {
//                    play();
//                }
//
//                if (PLAY_SERVICE_CMD.equals(action)
//                        || (SERVICE_CMD.equals(action) && CMD_PLAY.equals(cmd))) {
//                    pause();
//                }

                if (intent.getAction().equals("play")) {
//                    intent.getStringExtra("posN");
                    Log.d(TAG, "intent received played "+ intent.getData());
                    pause();


                }
            }
        };

        // Do the right thing when something else tries to play




        if (!mReceiverRegistered) {
            Log.d(TAG,"55555500");
            IntentFilter commandFilter = new IntentFilter();
            commandFilter.addAction("play");

            mContext.registerReceiver(mIntentReceiver, commandFilter);
            mReceiverRegistered = true;
        }
    }


//    public void nextSong(int pos, String path){
//
//
//        stop();
//        String path = musicTab1.data.pathM.get(pos);
//        playp( path) ;
//
//
//
//
//
//
//
//    }

    public void nextSong(String path){


        stop();

        playp( path) ;







    }





    public void prevSong(int pos){


        stop();
        String path = musicTab1.data.pathM.get(pos);
        System.out.println("11111"+pos+"---->"+path);
        Log.d(TAG,path);
        playp( path) ;







    }



}


