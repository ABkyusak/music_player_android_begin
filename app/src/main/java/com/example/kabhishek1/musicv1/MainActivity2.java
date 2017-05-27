package com.example.kabhishek1.musicv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kabhishek1.musicv1.jmusixmatch.MusixMatch;
import com.example.kabhishek1.musicv1.jmusixmatch.MusixMatchException;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.lyrics.Lyrics;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.Track;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.TrackData;

import java.util.List;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

public class MainActivity2 extends AppCompatActivity {

    public String mode = "111";
    public static  String pos;
    public String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        pos = i.getStringExtra("pos");
        Log.d(mode,pos);
        Log.d(mode,"activity before the 2nd tab layout started");




        setContentView(R.layout.activity_main);
//        Intent i = getIntent();
        //   username = i.getExtras().getString("name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("play Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Lyrics"));


        //  tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final android.support.v4.view.PagerAdapter adapter =  new com.example.kabhishek1.musicv1.PagerAdapterMusic(getSupportFragmentManager(), tabLayout.getTabCount());
//        final PagerAdapter adapter =

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                System.out.println("tab"+tab.getPosition());

                if(tab.getPosition() == 1){

                System.out.println("1111");
                    own();
                    musicPlayerTab2.settext("1111");


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);

        Log.d(mode, "inside the inflate");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.ShowAllPlayList:




                Log.d(mode,"show All playlist method invoked");

                Intent i = new Intent(this, GridViewActivity.class);
                startActivity(i);
//                finish();


                return (true);
            case R.id.about:
                //add the function to perform here
                return (true);


        }
        return (super.onOptionsItemSelected(item));
    }



    public void own(){







        SendfeedbackJob job = new SendfeedbackJob();
//        String str = new String();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                                                 Do something after 5s = 5000ms
                Log.d(mode,"af 5 sec end");
//                afterOnclick();
//                Log.d(TAG,p2.toString());

//                cc();

                String kk ="wwwww";
//                t.setText("1111");

                musicPlayerTab2.settext(str);


//                Log.d(mode,str);

//                t.setText(str);


            }
        }, 5000);



        job.execute( );
    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            try {
                testAPI();

            } catch (MusixMatchException e) {
                e.printStackTrace();
            }
            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }


    public void testAPI() throws MusixMatchException {


        String apiKey = "6f57bfe291fa1ab891e23c0449b9c804";
        MusixMatch musixMatch = new MusixMatch(apiKey);

        Log.d(mode,"authetication done");
//        if(trackName == null) {
//
        String trackName = "Hamari Adhuri Kahani";
        String artistName = "arijit singh";


//        }
       trackName = musicPlayerTab1.trackName;
      artistName = musicPlayerTab1.artistName;

        if(trackName == null){

            trackName = "Hamari Adhuri Kahani";
            artistName = "arijit singh";

        }

        Log.d(mode,"-------------"+trackName);
        Log.d(mode,"-------------"+artistName);



        Log.d(mode,"-------------"+trackName);
        Log.d(mode,"-------------"+artistName);


        // Track Search [ Fuzzy ]
        Track track = musixMatch.getMatchingTrack(trackName, artistName);
        TrackData data = track.getTrack();
        Log.d(mode ,"authetication done");


        System.out.println("11111");

        Log.d(mode,"data about the song that is Don't stop the Part");
        System.out.println("check"+" AlbumID : " + data.getAlbumId());
        System.out.println("check "+"Album Name : " + data.getAlbumName());
        System.out.println("check "+"Artist ID : " + data.getArtistId());
        System.out.println("check"+" Album Name : " + data.getArtistName());
        System.out.println("check"+" Track ID : " + data.getTrackId());


        int trackID = data.getTrackId();

        Lyrics lyrics = musixMatch.getLyrics(trackID);

        System.out.println("check "+"Lyrics ID       : " + lyrics.getLyricsId());
        System.out.println("check "+"Lyrics Language : " + lyrics.getLyricsLang());
        System.out.println("check"+"Lyrics Body     : " + lyrics.getLyricsBody());
        System.out.println("check"+"Script-Tracking-URL : " + lyrics.getScriptTrackingURL());
        System.out.println("check"+"Pixel-Tracking-URL : " + lyrics.getPixelTrackingURL());
        System.out.println("check"+"Lyrics Copyright : " + lyrics.getLyricsCopyright());

       str =lyrics.getLyricsBody();
//        t.setText(str);

        System.out.println("check------------------->"+ str);

//        for(int i =0; i < str.length(); i++)
//            System.out.println(str.charAt(i));
//        Log.d("tag123456", str.charAt(3)+" ");




//        txt = (TextView)findViewById(R.id.ly);

//             lyr = lyrics.getLyricsBody();
//        Log.d(TAG,lyr);
//        txt.setText(lyr);

//        Subtitle sub= musixMatch.getSubtitle(trackID);
//        System.out.println("check "+"Subtitle ID       : " + sub.getSubtitleId());
//        System.out.println("check "+"Subtitle Language : " + sub.getSubtitleLanguage());
//        System.out.println("check"+"Subtitle Body     : " + sub.getSubtitleBody());







//        String srt = sub.getSubtitleBody();
//        Log.d(TAG,srt);

//        System.out.println("check"+"Lyrics Copyright : " + lyrics.);






        // The following will search for tracks with matching artist_name 'Eminem'
        List<Track> tracks = musixMatch.searchTracks("", "Eminem", "", 10, 10, true);

        for (Track trk : tracks) {
            TrackData trkData = trk.getTrack();

            System.out.println("AlbumID : " + trkData.getAlbumId());
            System.out.println("Album Name : " + trkData.getAlbumName());
            System.out.println("Artist ID : " + trkData.getArtistId());
            System.out.println("Artist Name : " + trkData.getArtistName());
            System.out.println("Track ID : " + trkData.getTrackId());
            System.out.println();
        }
        Log.d(mode,"authetication done");



    }

    }
