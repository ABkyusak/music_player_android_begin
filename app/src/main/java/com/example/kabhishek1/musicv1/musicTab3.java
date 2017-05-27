package com.example.kabhishek1.musicv1;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class musicTab3 extends Fragment  {


    String mode = "articles";
    ProgressDialog pd;
    ListView list;
    ArrayList<String> pathS;
    ArrayList<String> SongName;

//    HashMap<String,ArrayList<String>>>
    ArrayList<String> ArtistName;
//    CustomAdapterArtist ca;
    String str;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.artists, container, false);

//        Button b1 = (Button) rootView.findViewById(R.id.ply);
//        b1.setOnClickListener(this);


        //   System.out.println("raja");
        ArtistName = new ArrayList<>();

        getAllArtist();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        CustomAdapterArtist mAdapter = new CustomAdapterArtist(ArtistName);
        // 4. set adapter

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // custom addonItemTouchListener is applied for this
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        System.out.println("clicked on item"+ position);
//                        Intent i = this.getActivity.getIntent();
                        Intent myIntent = new Intent(getActivity(), musicTab31.class);
                        myIntent.putExtra("artist", ArtistName.get(position));
                        myIntent.putExtra("artistImg",str);

                        getActivity().startActivity(myIntent);
                        // TODO Handle item click
                    }
                })
        );





//
//        mAdapter = new MoviesAdapter(movieList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//        prepareMovieData();



//        ListView lv = (ListView) rootView.findViewById(R.id.list);
//        ca = new CustomAdapterArtist(this.getActivity(),SongName);
//        lv.setAdapter(ca);
//        ImageView img = (ImageView) rootView.findViewById(R.id.imag);
//        img.setImageURI(Uri.parse(str));

        return rootView;
    }





    public void getAllArtist()
    {
        String[] mProjection =
                {
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,

                };

        Cursor c = this.getActivity().getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                mProjection,
                null,
                null,
                MediaStore.Audio.Artists.ARTIST + " ASC");



        if (c != null) {
            while (c.moveToNext()) {

//                System.out.println("artist----------->id"+c.getString(0));
                System.out.println("artist----------->artist name"+c.getString(1));
//                System.out.println("artist----------->number of tracks"+c.getString(2));
//                System.out.println("artist----------->number of albums"+c.getString(3));

                ArtistName.add(c.getString(1));


                String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.Media.COMPOSER,MediaStore.Audio.AudioColumns.ALBUM_ID,MediaStore.Audio.AudioColumns.ARTIST};

                String selection = MediaStore.Audio.Media.ARTIST + "=?";
                String[] selectionArgs = {c.getString(1)};

                Cursor cursor = this.getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection , selection, selectionArgs, null);

                if(cursor != null){
                    while(cursor.moveToNext()){

//                        System.out.println("artist------>pathSong ---"+cursor.getString(0));
//                        System.out.println("artist-----> title -------"+cursor.getString(1));
//                        SongName.add(cursor.getString(1));


                        Cursor cr;
                        cr = this.getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                                MediaStore.Audio.Albums._ID+ "=?",
                                new String[] {String.valueOf(cursor.getString(4))},
                                null);
                        if(cr != null){
                            while(cr.moveToNext()){
                                String albumart = cr.getString(1);
//                                System.out.println("artist---?album-art"+albumart);
                                str = albumart;
//                                break;
                            }
                            cr.close();
                        }
//                        break;
                    }

                    cursor.close();

                }






            }
            c.close();
        }



    }


}