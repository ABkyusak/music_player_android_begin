package com.example.kumarabhi.customlistviewv0;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
import android.util.Log;
import android.view.Menu;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {

    ListView lv;
    Context context;
    public static final String TAG = "123";

//    ArrayList prgmName;
    public static int [] prgmImages={R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images};
//    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         ArrayList<String> prgmNameList = new ArrayList<>();
        ArrayList<String> pathM = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        lv=(ListView) findViewById(R.id.listView);

        List<AudioModel> list = getAllAudioFromDevice(this);

        for(int i =0; i <4 ; i++) {
            prgmNameList.add(list.get(i).getaName());
            pathM.add(list.get(i).getaPath());
        }
//                 Log.d(TAG,"here");

        for(int i=0; i < prgmNameList.size();i++)
            Log.d(TAG,prgmNameList.get(i));

        lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages,pathM));
       // lv.setOnItemClickListener(this);


     /*   @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }*/


    }


    public List<AudioModel> getAllAudioFromDevice(final Context context) {
        final List<AudioModel> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                AudioModel audioModel = new AudioModel();
                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String artist = c.getString(3);

                audioModel.setaName(name);
                audioModel.setaAlbum(album);
                audioModel.setaArtist(artist);
                audioModel.setaPath(path);

//                Log.d(TAG, " Name :" + name);
//                Log.d(TAG, " Artist :" + path);

                tempAudioList.add(audioModel);
            }
            c.close();
        }

        return tempAudioList;
    }



}
