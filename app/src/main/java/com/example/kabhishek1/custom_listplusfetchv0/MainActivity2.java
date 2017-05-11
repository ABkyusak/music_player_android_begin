package com.example.kabhishek1.custom_listplusfetchv0;

/**
 * Created by k.abhishek1 on 19-04-2017.
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.kabhishek1.custom_listplusfetchv0.BackgroundSoundService.player;


/**
 * Created by kumar abhi on 4/18/2017.
 */

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "12345";
    //    MediaPlayer mediaPlayer;
    String PATH_TO_FILE;
    int length;
    public  String position;
    ArrayList<String> list;
    ArrayList<String> name;
    List<AudioModel> mo;
    int posN = 0;
    int posO;
    int prevN = 0;
    //    int posC;
    TextView txt;
    RemoteViews contentView;

//    NotificationManager manager;
//    NotificationCompat.Builder mBuilder;

    Notification notification ;

    NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 101;
    private static final String TAGN = "YourNotification";
    boolean gotFocus;
    AudioManager am;
    String desi;




    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    BroadcastReceiver receiver;

    MediaPlayer p2;

    public static int oneTimeOnly = 0;

    @Override
    public void onCreate(Bundle bundle) {

        Log.d(TAG,"MAin Activity2 Started");
        Log.d(TAG,"entry into activity");
        super.onCreate(bundle);
        setContentView(R.layout.activityl);
        txt = (TextView)findViewById(R.id.txtV);
        Button play = (Button)findViewById(R.id.ply);
        Button pause = (Button)findViewById(R.id.p);
        Button resume = (Button)findViewById(R.id.r);
        Button nxt = (Button)findViewById(R.id.next);
        Button prev = (Button)findViewById(R.id.prev);
        tx1 = (TextView)findViewById(R.id.textView2);
        tx2 = (TextView)findViewById(R.id.textView3);

        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);





        addAppToNotificationP();

        final Intent intent = getIntent();
        //get the array list of path
        list = new ArrayList<>();
        list = intent.getStringArrayListExtra("key");

        name = new ArrayList<>();
        name = intent.getStringArrayListExtra("name");




        desi = intent.getStringExtra("PATH");

        //get the current position on which the listView is clicked
        position = intent.getStringExtra("position");
        posO = Integer.parseInt(position);

        if(posN == 0) {
            posN = posO;
            prevN = posO;
        }


        Log.d(TAG,"position is here"+position);
        Log.d(TAG,"arrayList "+list.get(0));

//        Log.d(TAG,"list"+mo.get(0).getaName());



        txt.setText(list.get(posN));



        // code to receive the events from the notification


        IntentFilter filter = new IntentFilter();

        filter.addAction("Prev");
        filter.addAction("Play");
        filter.addAction("Next");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals("Prev")) {
                    Toast.makeText(context, "Intent Detected. prev is now  clicked", Toast.LENGTH_LONG).show();
                    prevSong();
                }else  if(intent.getAction().equals("Play")){
                    Toast.makeText(context, "Intent Detected. play is now  clicked", Toast.LENGTH_LONG).show();
                    MediaPlayer  p1;
                    p1 = BackgroundSoundService.curInstance();
                    int len = p1.getCurrentPosition();




                    if(p1.isPlaying() ) {


                        Log.d(TAG,"if statement"+p1.getCurrentPosition());
                        p1.pause();

                        contentView.setImageViewResource(R.id.image, R.drawable.pause);


                    }
                    else {
                        Log.d(TAG,"else statement");
                        contentView.setImageViewResource(R.id.image, R.drawable.play);
                        p1.seekTo(len);
                        p1.start();

                    }
                    mNotificationManager.notify(1, notification);




                }else if(intent.getAction().equals("Next")){
                    Toast.makeText(context, "Intent Detected. next is now  clicked", Toast.LENGTH_LONG).show();
                    nxtSong();

                }else{ if(intent.getAction().equals("Close")) {
                    Toast.makeText(context, "Close  hona hai ", Toast.LENGTH_LONG).show();

//                    manager.cancel(0);
                }

                }




            }
        };
        registerReceiver(receiver, filter);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"b4 playsong");
                playSong();
                Log.d(TAG,"af playsong");

//                                   p2 = new MediaPlayer();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                                                 Do something after 5s = 5000ms
                        Log.d(TAG, "af 5 sec end");
                        p2 = BackgroundSoundService.curInstance();
                        Log.d(TAG, p2.toString());

                        cc();


                    }
                }, 1000);

                Log.d(TAG, "af 5 sec beg");






//                                        System.out.println("finalTime"+p2.getCurrentPosition());
                                       /* startTime = p2.getCurrentPosition();
//                                        String s = (() finalTime);
//                                        Log.d(TAG,)


                                        if (oneTimeOnly == 0) {
                                            seekbar.setMax((int) finalTime);
                                            oneTimeOnly = 1;
                                        }

                                        tx2.setText(String.format("%d min, %d sec",
                                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                                finalTime)))
                                        );

                                        tx1.setText(String.format("%d min, %d sec",
                                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                                startTime)))
                                        );

                                        seekbar.setProgress((int)startTime);
                                        myHandler.postDelayed(UpdateSongTime,100);




*/
//                                       cc();


            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"pause clicked ");

                BackgroundSoundService.onStop();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BackgroundSoundService.onResume();



            }




        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nxtSong();


            }
        });



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prevSong();


            }
        });










    }


    @Override
    public void onClick(View v) {


    }

    public void cc()
    {
//        System.out.println("finalTime"+p2);
//        Log.d(TAG,p2.toString());

        finalTime = p2.getDuration();
        startTime = p2.getCurrentPosition();

//        if (oneTimeOnly == 0) {
        seekbar.setMax((int) finalTime);
        oneTimeOnly = 1;
//        }

        tx2.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        tx1.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
    }

    public void playSong() {


//        gotFocus = requestAudioFocusForMyApp(this);
//        if(gotFocus == true) {


        txt.setText(list.get(posN));



        // to update the name of the file in the notification
        contentView.setTextViewText(R.id.title, name.get(posN));
        mNotificationManager.notify(1, notification);



//        String str = list.get(posN);
//        contentView.setTextViewText(R.id.text, desi.substring(0,2));
        Log.d(TAG,"content"+desi.substring(0,2));



        Intent myService = new Intent(MainActivity2.this, BackgroundSoundService.class);
        stopService(myService);

        Intent svc = new Intent(getBaseContext(), BackgroundSoundService.class);
        svc.putExtra("Path", list.get(posN));
        Log.d(TAG, "ho gya");
        startService(svc);
        Log.d(TAG, "nhi ho gya");
//            mBuilder.setOngoing(true);




//        }


    }

    public void nxtSong(){

        posN = posN+1;

        if(posN == list.size())
            posN = 0;
        Integer nxtposition  = posN;
        Log.d(TAG,String.valueOf(nxtposition));

        String nxtPath = list.get(nxtposition);
//                Log.d(TAG,nxtPath);
        Log.d(TAG,nxtPath);


        contentView.setTextViewText(R.id.title, name.get(nxtposition));
        mNotificationManager.notify(1, notification);


        Intent myService = new Intent(MainActivity2.this, BackgroundSoundService.class);
        stopService(myService);
        txt.setText(list.get(posN));
        Intent svc = new Intent(getBaseContext(), BackgroundSoundService.class);
        svc.putExtra("Path", nxtPath);
        Log.d(TAG, "ho gya");
        startService(svc);
        Log.d(TAG, "nhi ho gya");



//                                                 Do something after 5s = 5000ms
        Log.d(TAG,"before next refernce intialised");

//            p2= BackgroundSoundService.curInstance();
//              Log.d(TAG,p2.toString());

//                cc();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                                                 Do something after 5s = 5000ms
                Log.d(TAG,"af 5 sec end");
                p2= BackgroundSoundService.curInstance();
                Log.d(TAG,p2.toString());

                cc();


            }
        }, 1000);





    }

    public void prevSong(){



        prevN = prevN-1;

        if(prevN == -1)
            prevN = list.size()-1;
        Integer nxtposition  = prevN;
        Log.d(TAG,"insode next button listener"+String.valueOf(nxtposition));

        String nxtPath = list.get(nxtposition);
//                Log.d(TAG,nxtPath);
        Log.d(TAG,nxtPath);

        contentView.setTextViewText(R.id.title, name.get(nxtposition));
        mNotificationManager.notify(1, notification);

//        BackgroundSoundService.nextSongb4();
        Intent myService = new Intent(MainActivity2.this, BackgroundSoundService.class);
        stopService(myService);


        txt.setText(list.get(prevN));

        Intent svc = new Intent(getBaseContext(), BackgroundSoundService.class);

        svc.putExtra("Path", nxtPath);

        Log.d(TAG, "service start hone se pehle ");
        startService(svc);
        Log.d(TAG, "service start ho  gya");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                                                 Do something after 5s = 5000ms
                Log.d(TAG,"af 5 sec end");
                p2= BackgroundSoundService.curInstance();
                Log.d(TAG,p2.toString());

                cc();


            }
        }, 1000);


    }


 /*   private void setupNotification() {


        Intent prevI = new Intent("Prev");
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0, prevI, 0);



        Intent nextI = new Intent();
        nextI.setAction("Next");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextI, 0);



        Intent playI = new Intent();
        playI.setAction("Play");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playI, 0);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.prev, "Prv", prevPendingIntent).build();
        NotificationCompat.Action action1 = new NotificationCompat.Action.Builder(R.drawable.next, "Nxt",nextPendingIntent ).build();
        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.drawable.play, "Play", playPendingIntent).build();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.images1)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setContentIntent(pendingIntent);


        mBuilder.addAction(action);
        mBuilder.addAction(action2);
        mBuilder.addAction(action1);


        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


//        manager.notify(0, mBuilder.build());





    }*/


    private void addAppToNotificationP() {

        /*Intent closeI = new Intent();
        closeI.setAction("Close");
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this, 0, closeI, 0);

        Intent prevI = new Intent("Prev");
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0, prevI, 0);



        Intent nextI = new Intent();
        nextI.setAction("Next");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextI, 0);



        Intent playI = new Intent();
        playI.setAction("Play");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playI, 0);



        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.prev, "Prv", prevPendingIntent).build();
        NotificationCompat.Action action1 = new NotificationCompat.Action.Builder(R.drawable.next, "Nxt",nextPendingIntent ).build();


        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.drawable.play, "Play", playPendingIntent).build();

//        NotificationCompat.Action action3 = new NotificationCompat.Action.Builder(R.drawable.close, "", closePendingIntent).build();
//         action2.


         mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.images1)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setContentIntent(pendingIntent);



//        mBuilder.setOngoing(true);

//        mBuilder.addAction(action3);
        mBuilder.addAction(action);
        mBuilder.addAction(action2);
        mBuilder.addAction(action1);




         manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, mBuilder.build());
        manager.notify(TAGN, NOTIFICATION_ID, mBuilder.build());*/


        // custom notification implementation

        int icon = R.drawable.music;
        long when = System.currentTimeMillis();
        notification = new Notification(icon, "Custom Notification", when);

        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        contentView = new RemoteViews(getPackageName(), R.layout.notification_view);

        contentView.setImageViewResource(R.id.image, R.drawable.play);
        contentView.setImageViewResource(R.id.image1,R.drawable.next);
        contentView.setImageViewResource(R.id.image2,R.drawable.prev);

//            contentView.setImageViewResource(R.id.image1, R.drawable.play);
//            contentView.setImageViewResource(R.id.image2, R.drawable.play);
//            contentView.setImageViewResource(R.id.image3, R.drawable.play);

        contentView.setTextViewText(R.id.title, "Music Player");
        contentView.setTextViewText(R.id.text, "This is a custom layout");
        notification.contentView = contentView;

//        Intent notificationIntent = new Intent(this, MainActivity2.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

//        notification.contentIntent = contentIntent;

        Intent nextI = new Intent();
        nextI.setAction("Next");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextI, 0);


        contentView.setOnClickPendingIntent(R.id.image1,
                nextPendingIntent);

//        notification.contentIntent= nextPendingIntent ;



        Intent playI = new Intent();
        playI.setAction("Play");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playI, 0);
        contentView.setOnClickPendingIntent(R.id.image,
                playPendingIntent);
//        notification.contentIntent = playPendingIntent;

        Intent prevI = new Intent("Prev");
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0, prevI, 0);
        contentView.setOnClickPendingIntent(R.id.image2,
                prevPendingIntent);

//        notification.contentIntent= prevPendingIntent ;

        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(1, notification);

    }

    @Override
    protected void onPause() {
        super.onPause();
        addAppToNotificationP();
    }

    protected void onDestroy(){
        super.onDestroy();
//        removeAppFromNotificationP();

    }


  /*  private void removeAppFromNotificationP() {
        manager.cancel(TAGN,NOTIFICATION_ID);
    }
*/


    private boolean requestAudioFocusForMyApp(final Context context) {
        am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for playback
        int result = am.requestAudioFocus(null,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d("AudioFocus", "Audio focus received");
            return true;
        } else {
            Log.d("AudioFocus", "Audio focus NOT received");
            return false;
        }
    }


    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = p2.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };


    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);

        super.onStop();
    }












}
