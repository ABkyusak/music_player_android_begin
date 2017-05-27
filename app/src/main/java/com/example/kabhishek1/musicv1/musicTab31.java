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

public class musicTab31 extends AppCompatActivity {

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
        songAlbumPath = new ArrayList<>();
        SongArtistNam = new ArrayList<>();
        ComposerNam = new ArrayList<>();
        Albu = new ArrayList<>();
        songAlbumImg = new ArrayList<>();
//        musicPlayerTab1.mm(songPath);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent i = getIntent();
        str = i.getStringExtra("artist");
        artist_img = i.getStringExtra("artistImg");

        System.out.println("clicked on item"+ str);


        prepareMovieData();
        getTrackFromArtist();


        img = (ImageView)findViewById(R.id.imgToolbar);
        if(songAlbumPath.get(0) == " " || songAlbumPath.get(0) == null)
            img.setImageResource(R.drawable.back5);
        else
            img.setImageURI(Uri.parse(songAlbumPath.get(0)));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CustomAdapterArtistNext(songPath,songName,songAlbumPath);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        System.out.println("clicked on item"+ position);
//                        Intent i = this.getActivity.getIntent();
//                        Intent myIntent = new Intent(this.get, musicTab31.class);
//                        myIntent.putExtra("artist", ArtistName.get(position));

//                        startActivity(myIntent);
//                        System.out.println("clicked on item"+songPath.get(position));


                        Intent myIntent = new Intent(view.getContext(),MainActivity2.class);
                        myIntent.putExtra("pos", String.valueOf(position));

                        musicPlayerTab1.mm(position,songName,SongArtistNam,ComposerNam,songPath,Albu,songAlbumImg);

                        startActivity(myIntent);





//                        musicPlayerTab1.mMusicPlayerC.nextSong(songPath.get(position));







//                        Log.d(TAG, " mIntentReceiver.onReceive " );










                        // TODO Handle item click
                    }
                })
        );



    }

    private void prepareMovieData() {



//        mAdapter.notifyDataSetChanged();
    }


    public void getTrackFromArtist()
    {





//                System.out.println("artist----------->id"+c.getString(0));
//                System.out.println("artist----------->artist name"+c.getString(1));
//                System.out.println("artist----------->number of tracks"+c.getString(2));
//                System.out.println("artist----------->number of albums"+c.getString(3));

        System.out.println("artist --------------->"+str );

//                ArtistName.add(c.getString(1));


                String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.Media.COMPOSER,MediaStore.Audio.AudioColumns.ALBUM_ID,MediaStore.Audio.AudioColumns.ARTIST};

                String selection = MediaStore.Audio.Media.ARTIST + "=?";
                String[] selectionArgs = {str};

                Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection , selection, selectionArgs, null);

                if(cursor != null){
                    while(cursor.moveToNext()){

                        System.out.println("artist------>pathSong ---"+cursor.getString(0));
                        System.out.println("artist-----> title -------"+cursor.getString(1));

                        songPath.add(cursor.getString(0));
                        songName.add(cursor.getString(1));
                        Albu.add(cursor.getString(2));
                        SongArtistNam.add(cursor.getString(5));
                        ComposerNam.add(cursor.getString(3));
                        songAlbumImg.add(artist_img);


//                        SongName.add(cursor.getString(1));


                        Cursor cr;
                        cr = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                                MediaStore.Audio.Albums._ID+ "=?",
                                new String[] {String.valueOf(cursor.getString(4))},
                                null);
                        if(cr != null){
                            while(cr.moveToNext()){
                                String albumart = cr.getString(1);
                                System.out.println("artist---?album-art"+albumart);

                                songAlbumPath.add(albumart);
//                                str = albumart;
//                                break;
                            }
                            cr.close();
                        }
//                        break;
                    }

                    cursor.close();

                }










    }
}