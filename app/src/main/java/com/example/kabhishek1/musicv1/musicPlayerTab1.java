package com.example.kabhishek1.musicv1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

public class musicPlayerTab1 extends Fragment implements View.OnClickListener {
    public static String mode ="123";


//    private FragmentCallback mCallback;

//    String mode = "123";
    ProgressDialog pd;
    ListView list;
    TextView txt;
    TextView txt1;
    TextView txt3;
    ImageView img;
    int posN = 0;
    int posO;
    int prevN = 0;
    int posC =0;

    public static String trackName;
    public static String artistName;
    public static Player mMusicPlayerC;
    private static DataClass data;
    Notification notification ;

    NotificationManager mNotificationManager;
    RemoteViews contentView;


    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    ;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1, tx2, tx3;
    BroadcastReceiver receiver;
    ImageButton play;
    Button prev;
    Button nxt;
    public static ArrayList<String> prgmNameLis;
    public static ArrayList<String> SongArtistNam;
    public static ArrayList<String> ComposerNam;
    public static ArrayList<String> path ;
    public static ArrayList<String> AsliArtis;
    public static ArrayList<String> Albu;
    public static ArrayList<String>  prgmIm;
    public  static  int posclicke;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;



    public static  ArrayList<String> tr;

    MediaPlayer p2;

    ShadowImageView imageViewAlbum;

    public static int oneTimeOnly = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.activityl, container, false);

        txt = (TextView) rootView.findViewById(R.id.txtV);
        txt.setSelected(true);
        txt1 = (TextView) rootView.findViewById(R.id.textAlbumArtist);
        txt3 = (TextView) rootView.findViewById(R.id.textComposer);

        play = (ImageButton) rootView.findViewById(R.id.ply);
        play.setImageResource(R.drawable.play);
        imageViewAlbum = (ShadowImageView) rootView.findViewById(R.id.image_view_album);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this.getActivity())) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + this.getActivity().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

//        System.out.println("try"+ tr.get(0));


//       c
//        pause = (Button)findViewById(R.id.p);
//         resume = (Button)findViewById(R.id.r);
         nxt = (Button) rootView.findViewById(R.id.next);
         prev = (Button) rootView.findViewById(R.id.prev);
        tx1 = (TextView) rootView.findViewById(R.id.textView2);
        tx2 = (TextView) rootView.findViewById(R.id.textView3);

        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekbar.setClickable(false);

//        posO= Integer.valueOf(MainActivity2.pos);
        posO= posclicke;

//        if (posN == 0) {
            posN = posO;
            prevN = posO;
            posC = posO;
//        }


        System.out.println("123 "+"activity started");



        play.setOnClickListener(this);
        nxt.setOnClickListener(this);
        prev.setOnClickListener(this);
        imageViewAlbum.setOnClickListener(this);

        addAppToNotificationP();

        if (mMusicPlayerC == null) {
            mMusicPlayerC = Player.getInstance(this.getActivity(), posN);
        }





//            Log.d(mode,"if statement"+mMusicPlayerC.player.getCurrentPosition());


        // fisrst song to be played after onClick
            mMusicPlayerC.stop();

            mMusicPlayerC.playp(path.get(posN));

       String str = prgmIm.get(posC);
        if( str == null || str == " " )
            imageViewAlbum.setImageResource(R.drawable.default_record_album);
        else {

           Bitmap bitmap = BitmapFactory.decodeFile(str);


            imageViewAlbum.setImageBitmap(getCroppedBitmap(bitmap));

        }
            imageViewAlbum.startRotateAnimation();
        txt.setText(prgmNameLis.get(posC));
        txt1.setText(SongArtistNam.get(posC));
        txt3.setText(ComposerNam.get(posC));


        trackName = prgmNameLis.get(posC);
        artistName = musicTab1.data.AsliArtist.get(posC);

        contentView.setImageViewResource(R.id.image, R.drawable.pause);
        play.setImageResource(R.drawable.pause);
        cc();







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
                    System.out.println("broadcast successfull");
//                    mMusicPlayerC.play(MainActivity.data.pathM.get(posN));




//                    BackgroundSoundService ser = new BackgroundSoundService();

//                    if (requestAudioFocusForMyApp(context) == false) {
//                        //Could not gain focus
//                        Log.d(TAG,"focus not gained");
//                        p1.pause();
////                        stopSelf();
//                    }

//                    Intent svc = new Intent(getBaseContext(), BackgroundSoundService.class);
//                    startService(svc);


                    Log.d(mode,"boolean value "+mMusicPlayerC.mAudioIsPlaying);

                    if(mMusicPlayerC.mAudioIsPlaying) {


                        Log.d(mode,"if statement"+mMusicPlayerC.player.getCurrentPosition());


                        contentView.setImageViewResource(R.id.image, R.drawable.pause);
                        mMusicPlayerC.pause();
                        imageViewAlbum.pauseRotateAnimation();

                    } else {
                        Log.d(mode,"else statement");

                        mMusicPlayerC.player.seekTo(mMusicPlayerC.player.getCurrentPosition());

                        mMusicPlayerC.playp(path.get(posN));
                        cc();
                        contentView.setImageViewResource(R.id.image, R.drawable.play);
                        imageViewAlbum.startRotateAnimation();

                    }
                    mNotificationManager.notify(1, notification);




                }else if(intent.getAction().equals("Next")){
                    Toast.makeText(context, "Intent Detected. next is now  clicked", Toast.LENGTH_LONG).show();
                    nxtSong();

                }else{ if(intent.getAction().equals("Close")) {
                    Toast.makeText(context, "Close  hona hai ", Toast.LENGTH_LONG).show();

//                    manager.cancel(0);
//                    prevSong();
                }

                }




            }
        };
        this.getActivity().registerReceiver(receiver, filter);


        return rootView;
    }






    public static ArrayList<String> AsliArtist;

    int posclicked;

    public static void mm(int posClicked, ArrayList<String> prgmNameList,  ArrayList<String> SongArtistName, ArrayList<String> ComposerName,ArrayList<String> pathM ,  ArrayList<String> Album,ArrayList<String> prgmImg){

        prgmNameLis = prgmNameList;
        SongArtistNam = SongArtistName;
        ComposerNam = ComposerName;
         path = pathM;

       Albu = Album;
         posclicke = posClicked;
        prgmIm = prgmImg;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ply:
//                Toast.makeText(, "play button clicked", Toast.LENGTH_SHORT).show();
                Log.d(mode, "play clicked");
                Bitmap bitmap;
//                addAppToNotificationP();

                String str = null;

                str = prgmIm.get(posC);
                if(str == " ")
                    imageViewAlbum.setImageResource(R.drawable.default_record_album);
                else {

                    bitmap = BitmapFactory.decodeFile(str);


                    imageViewAlbum.setImageBitmap(getCroppedBitmap(bitmap));

                }








                if (!mMusicPlayerC.mAudioIsPlaying) {

                    mMusicPlayerC.playp(path.get(posC));


                    txt.setText(prgmNameLis.get(posC));
                    txt1.setText(SongArtistNam.get(posC));
                    txt3.setText(ComposerNam.get(posC));



                    play.setImageResource(R.drawable.pause);
                    if(mMusicPlayerC.player.getCurrentPosition() > 0)
                        imageViewAlbum.resumeRotateAnimation();
                    else
                        imageViewAlbum.startRotateAnimation();

                } else {
                    mMusicPlayerC.pause();
                    txt.setText(prgmNameLis.get(posC));
                    txt1.setText(SongArtistNam.get(posC));
                    txt3.setText(ComposerNam.get(posC));
                    play.setImageResource(R.drawable.play);
                    imageViewAlbum.pauseRotateAnimation();;
                }

                trackName = prgmNameLis.get(posC);
                artistName = musicTab1.data.AsliArtist.get(posC);
//                musicPlayerTab2.ownmethod(trackName,artistName);

//                File image = new File("/storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1451606532276");
               this.getActivity().startService(new Intent(this.getActivity(), FloatingViewService.class));
//                this.getActivity().finish();
//                finish();

//

//                imageViewAlbum.startRotateAnimation();







                 contentView.setTextViewText(R.id.title, musicTab1.data.prgmNameList.get(posC));
                mNotificationManager.notify(1, notification);

                // to update the name of the file in the notification
//                contentView.setTextViewText(R.id.title, name.get(posN));
                cc();





                break;

            case R.id.prev:
                prevSong();
//                  str = "/storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1451606532276";





                break;
            case R.id.next:
                Log.d(mode,"next clicked");
                nxtSong();





                break;
            case R.id.image_view_album:
                Log.d(mode,"image view clicked");
                if (!mMusicPlayerC.mAudioIsPlaying) {

                    mMusicPlayerC.playp(path.get(posC));


                    txt.setText(prgmNameLis.get(posC));
                    txt1.setText(SongArtistNam.get(posC));
                    txt3.setText(ComposerNam.get(posC));



                    play.setImageResource(R.drawable.pause);
                    if(mMusicPlayerC.player.getCurrentPosition() > 0)
                        imageViewAlbum.resumeRotateAnimation();
                    else
                        imageViewAlbum.startRotateAnimation();

                } else {
                    mMusicPlayerC.pause();
                    txt.setText(prgmNameLis.get(posC));
                    txt1.setText(SongArtistNam.get(posC));
                    txt3.setText(ComposerNam.get(posC));
                    play.setImageResource(R.drawable.play);
                    imageViewAlbum.pauseRotateAnimation();;
                }
                break;
        }

    }

    public void cc()
    {
//        System.out.println("finalTime"+p2);
//        Log.d(TAG,p2.toString());

        finalTime = mMusicPlayerC.player.getDuration();
        startTime = mMusicPlayerC.player.getCurrentPosition();

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

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = Player.player.getCurrentPosition();
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



//    public void nxtSong() {
//
//        posN = posN + 1;
//
//
//        if (posN == path.size())
//            posN = 0;
//
//        posC = posN;
//        Integer nxtposition = posN;
//        Log.d(mode, String.valueOf(nxtposition));
//
//        String nxtPath = path.get(nxtposition);
////                Log.d(TAG,nxtPath);
//        Log.d(mode, nxtPath);
//
//        mMusicPlayerC.nextSong(posN);
//
//
//        txt.setText(prgmNameLis.get(posN));
//        txt1.setText(SongArtistNam.get(posN));
//        txt3.setText(ComposerNam.get(posN));
//
//        trackName = prgmNameLis.get(posN);
//        artistName = musicTab1.data.AsliArtist.get(posN);
//
//        contentView.setTextViewText(R.id.title, prgmNameLis.get(posN));
//        mNotificationManager.notify(1, notification);
//
//
//
//
//        // to set the the image in the playscreen and to rotate it
//        String str = musicTab1.prgmImag.get(posN);
//        if(str == " ")
//            imageViewAlbum.setImageResource(R.drawable.default_record_album);
//        else {
//
//            Bitmap bitmap = BitmapFactory.decodeFile(str);
//
//
//            imageViewAlbum.setImageBitmap(getCroppedBitmap(bitmap));
//        }
//
//        imageViewAlbum.startRotateAnimation();
//
//        cc();
//    }
//




    public void prevSong() {


        prevN = prevN - 1;

        if (prevN == -1)
            prevN = musicTab1.data.pathM.size() - 1;
        posC=prevN;

        Integer nxtposition = prevN;

        Log.d(mode, "insode next button listener" + String.valueOf(nxtposition));

        String nxtPath = musicTab1.data.pathM.get(nxtposition);
//                Log.d(TAG,nxtPath);
        Log.d(mode, nxtPath);

        mMusicPlayerC.prevSong(prevN);

        txt.setText(musicTab1.data.prgmNameList.get(prevN));
        txt1.setText(musicTab1.data.SongArtistName.get(prevN));
        txt3.setText(musicTab1.data.ComposerName.get(prevN));

        trackName = musicTab1.data.prgmNameList.get(prevN);
        artistName =     musicTab1.data.AsliArtist.get(prevN);



        String str = prgmIm.get(prevN);

        System.out.println("111"+ str);
        if(str == " ")
            imageViewAlbum.setImageResource(R.drawable.default_record_album);
        else {

            Bitmap bitmap = BitmapFactory.decodeFile(str);


            imageViewAlbum.setImageBitmap(getCroppedBitmap(bitmap));
        }
        imageViewAlbum.startRotateAnimation();

        contentView.setTextViewText(R.id.title, musicTab1.data.prgmNameList.get(prevN));
        mNotificationManager.notify(1, notification);
        cc();

    }


    public void nxtSong() {

        posN = posN + 1;


        if (posN == path.size())
            posN = 0;

        posC = posN;
        Integer nxtposition = posN;
        Log.d(mode, String.valueOf(nxtposition));

        String nxtPath = path.get(nxtposition);
//                Log.d(TAG,nxtPath);
        Log.d(mode, nxtPath);

        mMusicPlayerC.nextSong(path.get(posN));


        txt.setText(prgmNameLis.get(posN));
        txt1.setText(SongArtistNam.get(posN));
        txt3.setText(ComposerNam.get(posN));

        trackName = musicTab1.data.prgmNameList.get(posN);
        artistName = musicTab1.data.AsliArtist.get(posN);

        contentView.setTextViewText(R.id.title, prgmNameLis.get(posN));
        mNotificationManager.notify(1, notification);




        // to set the the image in the playscreen and to rotate it
        String str = prgmIm.get(posN);
        if(str == " ")
            imageViewAlbum.setImageResource(R.drawable.default_record_album);
        else {

            Bitmap bitmap = BitmapFactory.decodeFile(str);


            imageViewAlbum.setImageBitmap(getCroppedBitmap(bitmap));
        }

        imageViewAlbum.startRotateAnimation();

        cc();
    }


    private void addAppToNotificationP() {




        // custom notification implementation

        int icon = R.drawable.music;
        long when = System.currentTimeMillis();
        notification = new Notification(icon, "Custom Notification", when);

//        mNotificationManager = (NotificationManager)getSystemService(getActivity().NOTIFICATION_SERVICE);
          mNotificationManager = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );

        contentView = new RemoteViews(this.getActivity().getPackageName(), R.layout.notification_view);
//        contentView = new Rem
        System.out.println("123"+"notification entered");

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
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, nextI, 0);


        contentView.setOnClickPendingIntent(R.id.image1,
                nextPendingIntent);

//        notification.contentIntent= nextPendingIntent ;



        Intent playI = new Intent();
        playI.setAction("Play");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, playI, 0);
        contentView.setOnClickPendingIntent(R.id.image,
                playPendingIntent);
//        notification.contentIntent = playPendingIntent;

        Intent prevI = new Intent("Prev");
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, prevI, 0);
        contentView.setOnClickPendingIntent(R.id.image2,
                prevPendingIntent);

//        notification.contentIntent= prevPendingIntent ;

        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
//        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(1, notification);

    }


//    .setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//
//
//
//
//
//
//
//
//
//
//
//            if (mMusicPlayerC == null) {
//                mMusicPlayerC = BackgroundSoundService.getInstance(getApplicationContext(),posN);
//            }
//
//
//
//
//            if(!mMusicPlayerC.mAudioIsPlaying) {
//
//                mMusicPlayerC.play();
//
//
//                txt.setText(name.get(posN));
//                txt1.setText(album.get(posN));
//                txt3.setText(Composer.get(posN));
//                play.setImageResource(R.drawable.pause);
//            }else{
//                mMusicPlayerC.pause();
//                txt.setText(name.get(posN));
//                txt1.setText(album.get(posN));
//                txt3.setText(Composer.get(posN));
//                play.setImageResource(R.drawable.play);
//            }
//
//
//
//
//
//
//            // to update the name of the file in the notification
////            contentView.setTextViewText(R.id.title, name.get(posN));
////            mNotificationManager.notify(1, notification);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            cc();
//
//
//        }
//    });
//
//


    @Override
    public void onStop(){
        super.onStop();

//        if(receiver != null)
//       this.getActivity().unregisterReceiver(receiver);
    }


    public static Bitmap parseAlbum(File file) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        try {
            metadataRetriever.setDataSource(file.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            Log.d(mode, "parseAlbum: ", e);
        }
        byte[] albumData = metadataRetriever.getEmbeddedPicture();
        if (albumData != null) {
            return BitmapFactory.decodeByteArray(albumData, 0, albumData.length);
        }
        return null;
    }


    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                System.out.println("111+ permission granted");
            } else { //Permission is not available
                Toast.makeText(this.getActivity(),
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                this.getActivity().finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
