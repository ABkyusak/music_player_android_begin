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

public class musicTab2 extends Fragment  {


    String mode = "articles";
    ProgressDialog pd;
    ListView list;
    ArrayList<String> pathS;
    ArrayList<String> SongName;

    //    HashMap<String,ArrayList<String>>>
    ArrayList<String> Albums;
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
        Albums = new ArrayList<>();

        getAllAlbums();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        CustomAdapterArtist mAdapter = new CustomAdapterArtist(Albums);
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
                        Intent myIntent = new Intent(getActivity(), musicTab21.class);
                        myIntent.putExtra("album", Albums.get(position));
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


    public void getAllAlbums(){



        String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
                android.provider.MediaStore.Audio.Albums.ALBUM };

        Cursor cursor = this.getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns, null, null, null);


        if (cursor != null) {
            while (cursor.moveToNext()) {

                System.out.println("Album   "+cursor.getString(1));
                Albums.add(cursor.getString(1));
            }
            cursor.close();






            }





    }









}