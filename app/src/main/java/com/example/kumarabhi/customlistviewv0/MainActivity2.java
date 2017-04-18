package com.example.kumarabhi.customlistviewv0;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kumar abhi on 4/18/2017.
 */

public class MainActivity2 extends Activity implements View.OnClickListener {
    public static final String TAG = "12345";
    MediaPlayer mediaPlayer;
    String PATH_TO_FILE;
    int length;
    @Override
    public void onCreate(Bundle bundle) {
        Log.d(TAG,"entry into activity");
        super.onCreate(bundle);
        setContentView(R.layout.activityl);
        TextView txt = (TextView)findViewById(R.id.txtV);
        Button play = (Button)findViewById(R.id.ply);
        Button pause = (Button)findViewById(R.id.p);
        Button resume = (Button)findViewById(R.id.r);

        Intent intent = getIntent();
        String desi = intent.getStringExtra("PATH");
        Log.d(TAG,desi);



        txt.setText(desi);


        PATH_TO_FILE = desi;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(PATH_TO_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.getCurrentPosition() == 0) {
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }else{
                    mediaPlayer.seekTo(length);

                    mediaPlayer.start();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                     length = mediaPlayer.getCurrentPosition();
                }
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(length);
                mediaPlayer.start();

            }
        });







    }


    @Override
    public void onClick(View v) {


    }
}
