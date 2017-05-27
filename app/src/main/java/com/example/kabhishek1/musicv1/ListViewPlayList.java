package com.example.kabhishek1.musicv1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by k.abhishek1 on 15-05-2017.
 */

public class ListViewPlayList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapterArtistNext mAdapter;
    public  ArrayList<String> songPath;
    public ArrayList<String> songName;
    public ArrayList<String> songAlbumPath;
    public ArrayList<String> songAlbumImg;
    String str;

    ImageView img;
    String artist_img;



    public  ArrayList<String> SongArtistNam;
    public  ArrayList<String> ComposerNam;


    public  ArrayList<String> Albu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists);
//        songs = new ArrayList<>();
        songPath = new ArrayList<>();
        songName = new ArrayList<>();
//        songAlbumPath = new ArrayList<>();
        SongArtistNam = new ArrayList<>();
        ComposerNam = new ArrayList<>();
        Albu = new ArrayList<>();
        songAlbumImg = new ArrayList<>();
//        musicPlayerTab1.mm(songPath);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String play_id = intent.getStringExtra("playListid");

        getPlayList(play_id);


        System.out.println("clicked on item" + str);


        img = (ImageView) findViewById(R.id.imgToolbar);

            img.setImageResource(R.drawable.back5);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CustomAdapterArtistNext(songPath, songName, songAlbumPath);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        System.out.println("clicked on item" + position);
//                        Intent i = this.getActivity.getIntent();
//                        Intent myIntent = new Intent(this.get, musicTab31.class);
//                        myIntent.putExtra("artist", ArtistName.get(position));

//                        startActivity(myIntent);
//                        System.out.println("clicked on item"+songPath.get(position));


                        Intent myIntent = new Intent(view.getContext(), MainActivity2.class);
                        myIntent.putExtra("pos", String.valueOf(position));
                        System.out.println("playlist------------------------------>"+ songAlbumPath.size() );



                        musicPlayerTab1.mm(position, songName, SongArtistNam, ComposerNam, songPath, Albu, songAlbumPath);

                        startActivity(myIntent);


//                        musicPlayerTab1.mMusicPlayerC.nextSong(songPath.get(position));


//                        Log.d(TAG, " mIntentReceiver.onReceive " );


                        // TODO Handle item click
                    }
                })
        );
    }




    public void getPlayList(String playlistI) {

        songPath = new ArrayList<>();
        songName = new ArrayList<>();
        SongArtistNam = new ArrayList<>();
        ComposerNam = new ArrayList<>();
        Albu = new ArrayList<>();
        songAlbumPath = new ArrayList<>();


        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME};


        Cursor playListCursor = getContentResolver().query(uri, projection, MediaStore.Audio.Playlists._ID + "=" + playlistI + "", null, null);


        String id = "0";
        String name;

        int i = 0;
        if (playListCursor != null) {
            while (playListCursor.moveToNext()) {

                System.out.println("playlist" + "no " + i);


                id = playListCursor.getString(0);
                name = playListCursor.getString(1);


                System.out.println("playlist  " + id);
                System.out.println("playlist  " + name);
                i++;


            }

            int playListid = Integer.parseInt(id);

            System.out.println("playlist" + "current showing the playlist " + playListid);
            if (playListid > 0) {

                String[] pro = {

                        MediaStore.Audio.Playlists.Members.AUDIO_ID,
                        MediaStore.Audio.Playlists.Members.ARTIST,
                        MediaStore.Audio.Playlists.Members.TITLE,
                        MediaStore.Audio.Playlists.Members._ID


                };


                Cursor pl;
                Uri uu = MediaStore.Audio.Playlists.Members.getContentUri("external",playListid );

                pl = getContentResolver().query(uu, pro,MediaStore.Audio.Media.IS_MUSIC +" != 0 ",
                        null,
                        null);


                if (pl != null) {
                    while (pl.moveToNext()) {


                        id = pl.getString(0);
                        name = pl.getString(1);
                        String title = pl.getString(2);
//                        arrayList.add(name);


                        System.out.println("playlist  " + "song_id  " + id);
                        System.out.println("playlist  " + "song_artist_name " + name);
                        System.out.println("playlist  " + "song_title " + title);
                        System.out.println("playlist " + "song fetched from playlist now query build for finding the location");


                        //query to fethc the song location


                        Uri u = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


                        String[] p = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.Media._ID,MediaStore.Audio.AudioColumns.ALBUM_ID,MediaStore.Audio.AudioColumns.ARTIST,MediaStore.Audio.AudioColumns.COMPOSER};

                        Cursor c = getContentResolver().query(u, p, MediaStore.Audio.Media._ID + " = " + id + "", null, null);

                        if (c != null) {
                            while (c.moveToNext()) {


                                String path = c.getString(0);
                                String tit = c.getString(1);
                                String album = c.getString(2);
                                String media_id = c.getString(3);
                                String album_id = c.getString(4);

                                System.out.println("playlist  " + "-----------found song path-------------- " + path);
                                System.out.println("playlist  " + "found song title " + tit);

                                System.out.println("playlist  " + "found song album  " + album);
                                System.out.println("playlist  " + "found song album-id " + album_id);
                                System.out.println("playlist  " + "found song album-path " + musicTab1.pathAlbum.get(album_id));
//
                                songPath.add(path);
                                songName.add(tit);
                                SongArtistNam.add(c.getString(5));
                                ComposerNam.add(c.getString(6));
                                Albu.add(c.getString(2));
                                songAlbumPath.add(musicTab1.pathAlbum.get(album_id));


//


                            }
                            c.close();
                        }


                    }
                    pl.close();
                }


            }


            playListCursor.close();
        }



    }



    @Override
    protected void onPause() {
        super.onPause();
//        addAppToNotificationP();
    }

    protected void onDestroy(){
        super.onDestroy();
//        removeAppFromNotificationP();

    }

}
