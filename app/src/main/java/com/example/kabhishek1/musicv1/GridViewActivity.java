package com.example.kabhishek1.musicv1;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kumar abhi on 5/13/2017.
 */

public class GridViewActivity extends Activity {

    public String TAG = "123";


    GridView gridview;
    ArrayList<String> PlayListNameList;
    ArrayList<String> PlayListImages;
    ArrayList<String> PlayListIdd;
    HashMap<String, String> AlbumMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PlayListNameList =getAllPlayList();
        PlayListImages = new ArrayList<>();

        Log.d(TAG, "grid view activity called ");

        getAlbum();

//        AlbumMap = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout);
        gridview = (GridView) findViewById(R.id.customgrid);


        System.out.println("playlist-----size"+ PlayListNameList.size()+ " "+ PlayListImages.size()+ "  "+ PlayListIdd.size());

//        for(int i =0 ; i < 4; i++){
//            System.out.println("playlist"+PlayListNameList.get(i));
//            System.out.println("playlist"+PlayListImages.get(i));
//            System.out.println("playlist"+PlayListIdd.get(i));
//
//        }
//        gridview.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        gridview.setAdapter(new CustomAdapterGridView(this, PlayListNameList,  PlayListImages,PlayListIdd));


    }


    public void getAlbum() {

//        ArrayList arrayList = new ArrayList<String>();


        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME};


        Cursor playListCursor = getContentResolver().query(uri, projection, null, null, null);


        String id = "0";
        String name;

        int flag = 0;

        int i = 0;
        if (playListCursor != null) {
            while (playListCursor.moveToNext()) {

                System.out.println("playlist" + "no " + i);


                id = playListCursor.getString(0);
                name = playListCursor.getString(1);
//                arrayList.add(name);


                System.out.println("playlist  " + id);
                System.out.println("playlist  " + name);
                i++;


                int playListid = Integer.parseInt(id);

                flag = 0;

                System.out.println("playlist" + "current showing the playlist " + playListid);
                if (playListid > 0) {

                    String[] pro = {

                            MediaStore.Audio.Playlists.Members.AUDIO_ID,
                            MediaStore.Audio.Playlists.Members.ARTIST,
                            MediaStore.Audio.Playlists.Members.TITLE,
                            MediaStore.Audio.Playlists.Members._ID


                    };


                    Cursor pl = null;



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


                            String[] p = {MediaStore.Audio.AudioColumns.ALBUM_ID};

                            Cursor c = getContentResolver().query(u, p, MediaStore.Audio.Media._ID + " = " + id + "", null, null);

                            if (c != null) {
                                while (c.moveToNext()) {


                                    String album_id = c.getString(0);

                                    System.out.println("album-check  " + "-----------found song path--------------" + album_id);


//                                    MainActivity m  = new MainActivity();
//                                    m.pathAlbum;
                                    System.out.println("playlist----->pathAlbum"+  musicTab1.pathAlbum.get(album_id)+"------->   "+playListid);



                                        PlayListImages.add(musicTab1.pathAlbum.get(album_id));


//                                    if (MainActivity.pathAlbum.containsKey(album_id)) {
//
//                                        osImages.add("/storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1494235810044");
                                        flag = 1;
//
//                                        System.out.println("album-check"+ MainActivity.pathAlbum.get(album_id)+"------->   "+playListid);
                                        break;
//                                    }


//


                                }
                                c.close();
                            }
//



                        }
                        pl.close();
                    }


                }
            }
        }


        playListCursor.close();
//        return arrayList;
    }


    public ArrayList<String> getAllPlayList() {

        ArrayList<String> arrayList = new ArrayList<String>();
        PlayListIdd = new ArrayList<String>();




        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Playlists._ID,MediaStore.Audio.Playlists.NAME};



        Cursor playListCursor= getContentResolver().query(uri, projection, null, null, null);


        String id="0";
        String name;

        int i = 0;
        if (playListCursor != null) {
            while (playListCursor.moveToNext()) {

                System.out.println("playlist"+"no "+ i);


                id = playListCursor.getString(0);
                name = playListCursor.getString(1);

                arrayList.add(name);
              PlayListIdd.add(id);


                System.out.println("playlist----------> id "+id);
                System.out.println("playlist----------->name  "+name);
                i++;


            }

            playListCursor.close();
        }

        return  arrayList;

    }

}




