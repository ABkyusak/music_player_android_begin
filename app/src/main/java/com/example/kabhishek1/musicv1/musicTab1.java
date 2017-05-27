package com.example.kabhishek1.musicv1;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class musicTab1 extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemLongClickListener {


    private static final int REQUEST_READ_PERMISSION =786 ;
    String mode = "articles";
    ProgressDialog pd;
    public static String trackName;
    public static String artistName;

    ListView lv;
  CustomAdapter ca;

    Context context;
    public static final String TAG = "123";
    ArrayList<String> pathM ;
    private ActionMode mActionMode;
    public SparseBooleanArray mSelectedItemsIds;
    ArrayList<String> prgmNameList;
    ArrayList<String> SongArtistName;
    ArrayList<String> ComposerName;
    ArrayList<String> AsliArtist;
    ArrayList<String> Album;
    EditText editsearch;
    private BroadcastReceiver receiver;

    List<AudioModel> tempAudioList;
    ArrayList<String> arrayList; // list of all playList




    public static DataClass data ;
    HashMap<String,ArrayList<String>> pathMusic ;
    static HashMap<String,String> pathAlbum ;

//    public String[] prgmImag;

    public static ArrayList<String> prgmImag;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            openFilePicker();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
        } else {
//            openFilePicker();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        pathM = new ArrayList<>();
        prgmNameList = new ArrayList<>();
        prgmImag =  new ArrayList<>();
        SongArtistName =new ArrayList<>();
        ComposerName = new ArrayList<>();
        AsliArtist = new ArrayList<>();
        pathAlbum = new HashMap< >();
        Album = new ArrayList<>();
        mSelectedItemsIds = new SparseBooleanArray();

        View rootView = inflater.inflate(R.layout.activitytab1, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        editsearch = (EditText) rootView.findViewById(R.id.search);
        requestPermission();


        afterOnclick();


        return rootView;
    }




    public void afterOnclick()
    {


        pathM.clear();
        prgmNameList.clear();
        SongArtistName.clear();
        ComposerName.clear();
        AsliArtist.clear();
        Album.clear();
        mSelectedItemsIds.clear();

        Log.d(TAG,"inside afterOnClick");

        List<AudioModel> list;
        getAllAudioFromDevice();
        getAllAlbumFromDevice();
        setAlbumIdAndPics();

        list =tempAudioList;

        System.out.println("123-----=====--------"+tempAudioList.get(0).getAlbumArt());



        for (int i = 0; i < list.size(); i++) {
            prgmNameList.add(list.get(i).getaName());
            pathM.add(list.get(i).getaPath());

            System.out.println("image-uri"+list.get(i).getaArtist());




            if(list.get(i).getAlbumArt() == null)
                prgmImag.add(" ");
            else
                prgmImag.add(list.get(i).getAlbumArt());

            System.out.println("image-uri ------> "+list.get(i).getAlbumArt());

            SongArtistName.add(list.get(i).getaAlbum());
            ComposerName.add(list.get(i).getComposer());
            AsliArtist.add(list.get(i).getAsliArtist());
            Album.add(list.get(i).getaAlbum());

        }


        if(data == null)
            data = DataClass.getInstance(prgmNameList,pathM,SongArtistName,ComposerName,AsliArtist,Album);


//        lv.setOnItemLongClickListener(this);

//          lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        ca = new CustomAdapter(this.getActivity(), prgmNameList, prgmImag, pathM,mSelectedItemsIds);
        lv.setAdapter(ca);



        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                System.out.println("987"+ text);

                ca.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });






        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"clicked on list view");

                if (mActionMode == null) {

//                    musicPlayerTab1 ob = new musicPlayerTab1();
//                    musicPlayerTab1.mm(prgmNameList);

                    Intent myIntent = new Intent(getActivity(), MainActivity2.class);
                    myIntent.putExtra("pos", String.valueOf(position));

                    musicPlayerTab1.mm(position,data.prgmNameList,data.SongArtistName,data.ComposerName,data.pathM,data.Album,prgmImag);

                    getActivity().startActivity(myIntent);

                }else{
                onListItemSelect(position,view);
                view.setBackgroundColor(0x9934B5E4);
            }
                trackName = musicTab1.data.prgmNameList.get(position);
                artistName = musicTab1.data.AsliArtist.get(position);



            }
        });



    }


    public void getAllAudioFromDevice() {



        tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.Media.COMPOSER,MediaStore.Audio.AudioColumns.ALBUM_ID,MediaStore.Audio.AudioColumns.ARTIST};
        Cursor c = this.getActivity().getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                AudioModel audioModel = new AudioModel();
                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String composer = c.getString(3);
                String artist = c.getString(4);
                String Artist = c.getString(5);
//                String albumArt = c.getString(4);

                audioModel.setaName(name);

                audioModel.setaAlbum(album);
                audioModel.setComposer(composer);

//                audioModel.setaAlbumId(artist);
                audioModel.setaAlbumId(artist);

                audioModel.setaPath(path);
                audioModel.setAsliArtist(Artist);
//                audioModel.setaArtist(artist);
//                audioModel.setAlbumArt(albumArt);

//                Log.d(TAG, " Name :" + );
//                Log.d("playlist"+" inside the  album  :" + album);
                System.out.println("playlist"+" inside the  getAllAudioFromDevice artist  :" + artist);

//                hm.put(artist,new Pair<path,>())
//                pathMusic.put(artist,path);
//                  pathMusic.put(artist,)

//                if (pathMusic.get(artist) == null) {
//                    pathMusic.put(artist, new ArrayList<String>());
//                }
//
//
//                pathMusic.get(artist).add(path);


                tempAudioList.add(audioModel);
            }
            c.close();
        }

//        return tempAudioList;
    }

    public void setAlbumIdAndPics() {

        Log.d(TAG, "sfmsdfdofodkoskfdofkdofkdokokfosdkfksdfkdofkdfokdofk");

//        for  (int i =0 ;i < list.size(); i++){
//
//             String alb_id = list.get(i).getaAlbumId();
//            Log.d("TAG",alb_id +"----->   "+ pathAlbum.get(alb_id));
//            list.get(i).setAlbumArt(pathAlbum.get(alb_id));
//        }
//        Log.d("TAG",tempAudioList.size());
        System.out.println("123" + tempAudioList.size());
        for (AudioModel temp : tempAudioList) {

            String alb_id = temp.getaAlbumId();
            System.out.println("album-check" + temp.getaAlbumId() + " " + pathAlbum.get(alb_id));

//            Log.d("TAG","1111111111111" + alb_id);
            if (pathAlbum.get(alb_id) != null) {
                temp.setAlbumArt(pathAlbum.get(alb_id));
                System.out.println("123 " + "entered");
            } else {
                temp.setAlbumArt(" ");
            }

//            Log.d("TAG",temp.getAlbumArt()+"---------->"+temp.getaPath());

        }
    }


    public  void getAllAlbumFromDevice()  {
//         List<AudioModel> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART};
        Cursor c = this.getActivity().getContentResolver().query(uri, projection, null, null, null);







        if (c != null) {
            while (c.moveToNext()) {

//                AudioModel audioModel = new AudioModel();
                String path = c.getString(1);
                String name = c.getString(0);

//                String album = c.getString(2);
//                String artist = c.getString(3);
//                String albumArt = c.getString(4);

//                audioModel.setaName(name);
//                audioModel.setaAlbum(album);
//                audioModel.setaArtist(artist);
//                audioModel.setaPath(path);
//                audioModel.setAlbumArt(albumArt);


//                Log.d(TAG, " album-id--------------------------------------------------> :" + path);
//                Log.d(TAG, " album-art--------------------------------------------------> :" + name);
                System.out.println("playlist"+"inside the getAllAlbum     "+name+"----->" + path);
                pathAlbum.put(name,path);

//                tempAudioList.add(audioModel);
            }
            c.close();
        }


    }

    public ArrayList<String> getAllPlayList(final Context context) {

        arrayList = new ArrayList<String>();




        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Playlists._ID,MediaStore.Audio.Playlists.NAME};



        Cursor playListCursor= context.getContentResolver().query(uri, projection, null, null, null);


        String id="0";
        String name;

        int i = 0;
        if (playListCursor != null) {
            while (playListCursor.moveToNext()) {

                System.out.println("playlist"+"no "+ i);


                id = playListCursor.getString(0);
                name = playListCursor.getString(1);
                arrayList.add(name);


                System.out.println("playlist  "+id);
                System.out.println("playlist  "+name);
                i++;


            }

//          int playListid = Integer.parseInt(id);
//
//            System.out.println("playlist"+"current showing the playlist "+ playListid);
//            if(playListid > 0){
//
//                String[] pro = {
//
//                        MediaStore.Audio.Playlists.Members.AUDIO_ID,
//                        MediaStore.Audio.Playlists.Members.ARTIST,
//                        MediaStore.Audio.Playlists.Members.TITLE,
//                        MediaStore.Audio.Playlists.Members._ID
//
//
//
//                };
//
//
//                playListCursor = null;
//
//
//
//                playListCursor = this.managedQuery(
//                        MediaStore.Audio.Playlists.Members.getContentUri("external",playListid ),
//                        pro,
//                        MediaStore.Audio.Media.IS_MUSIC +" != 0 ",
//                        null,
//                        null);
//
//
//
//                if (playListCursor != null) {
//                    while (playListCursor.moveToNext()) {
//
//
//                        id = playListCursor.getString(0);
//                        name = playListCursor.getString(1);
//                        String title = playListCursor.getString(2);
////                        arrayList.add(name);
//
//
//                        System.out.println("playlist  "+"song_id  " + id);
//                        System.out.println("playlist  "+"song_artist_name " + name);
//                        System.out.println("playlist  "+"song_title " + title);
//                        System.out.println("playlist " + "song fetched from playlist now query build for finding the location");
//
//
//
//                        //query to fethc the song location
//
//
//                        Uri u = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//
//                        String[] p = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.Media._ID};
//
//                        Cursor c = context.getContentResolver().query(u, p, MediaStore.Audio.Media._ID+ " = "+id+"", null, null);
//
//                        if (c != null) {
//                            while (c.moveToNext()) {
//
//
//                                String path = c.getString(0);
//                                String n = c.getString(1);
//                                String album = c.getString(2);
//                                String artist = c.getString(3);
//
//                                System.out.println("playlist  "+"-----------found song path--------------"+path);
//                                System.out.println("playlist  "+"found song"+n);
//
//                                System.out.println("playlist  "+"found song"+ album);
//                                System.out.println("playlist  "+"found song"+ artist);
////
//
//
//
////
//
//
//
//                            }
//                            c.close();
//                        }
//
//
//
//                    }
//                }
//
//
//
//
//
//
//
//
//                }







            playListCursor.close();
        }

        return  arrayList;

    }





    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        afterOnclick();
        Log.d(TAG,"inside the LongItem itemClick Listener ");
        onListItemSelect(position,view);
        return true;
    }


    private void onListItemSelect(int position,View view) {

        toggleSelection(position, view);


        boolean hasCheckedItems = getSelectedCount() > 0;

        if (hasCheckedItems && mActionMode == null) {
            // there are some selected items, start the actionMode
            mActionMode = this.getActivity().startActionMode(new ActionModeCallback());
            Log.d(TAG, "there are some selected items, start the actionMode");
//            mActionMode.setHomeButtonEnabled(true);
//            mActionMode.setDisplayHomeAsUpEnabled(true);
        } else if (!hasCheckedItems && mActionMode != null) {
            // there no selected items, finish the actionMode
            mActionMode.finish();
            afterOnclick();
            Log.d(TAG, "there no selected items, finish the actionMode");
        }

        if (mActionMode != null) {
            mActionMode.setTitle(String.valueOf(getSelectedCount()) + " selected");
//            view.setBackgroundColor(Color.parseColor("#222222"));
//            view.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4: Color.TRANSPARENT);

            Log.d(TAG, "selected item is " + position);
        }
    }

        public void toggleSelection(int position,View view) {
            Log.d(TAG,"inside toggleSelection now selectView will be called");

            selectView(position, !mSelectedItemsIds.get(position),view);
        }


        public void removeSelection() {
            mSelectedItemsIds = new SparseBooleanArray();
            ca.notifyDataSetChanged();
        }

        public void selectView(int position, boolean value,View view) {
            Log.d(TAG,"inside selectView now value will be called");
            if (value) {
                mSelectedItemsIds.put(position, value);
//           view.setBackgroundColor(0x9934B5E4);

                Log.d(TAG,"new item added at position"+position);
            }
            else {
                mSelectedItemsIds.delete(position);
                Log.d(TAG,"item deleted at position"+position);
            }

            ca.notifyDataSetChanged();
        }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }



    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // inflate contextual menu
            mode.getMenuInflater().inflate(R.menu.onlongclick, menu);
//            mode.setHomeButtonEnabled(true);
//            ab.setDisplayHomeAsUpEnabled(true);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }





        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.delete:


                    // retrieve selected items and delete them out
                    SparseBooleanArray selected = getSelectedIds();
                    System.out.println("123"+selected.size());

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Do you want to delete the selected files ?");
                    builder1.setCancelable(true);

//                    List<String> mAnimals = new ArrayList<String>();
//                    mAnimals.add("Cat");
//                    mAnimals.add("Dog");
//                    mAnimals.add("Horse");
//                    mAnimals.add("Elephant");
//                    mAnimals.add("Rat");
//                    mAnimals.add("Lion");

//                    final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
//                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//                    dialogBuilder.setTitle("Animals");
//                    dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int item) {
//                            String selectedText = Animals[item].toString();  //Selected item in listview
//
//                            Log.d(TAG,"clicked in the listview in the alaerbox");
//                        }
//                    });
                    //Create alert dialog object via builder
//                    AlertDialog alertDialogObject = dialogBuilder.create();
                    //Show the dialog
//                    alertDialogObject.show();

//                    builder1.setPositiveButton(
//                            "Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//
//
//                                    dialog.cancel();
//                                }
//                            });
////
//                    builder1.setNegativeButton(
//                            "No",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();




                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            int key = selected.keyAt(i);
                            System.out.println("123"+key+"  "+selected.valueAt(i));
//                             Log.d(TAG,toString(key));
//                          File file = new File(pathM.get(key));
//                          boolean deleted = file.delete();
                            scanaddedFile(pathM.get(key));
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                                 Do something after 5s = 5000ms
                                    Log.d(TAG,"af 5 sec end");
                                    afterOnclick();
//                Log.d(TAG,p2.toString());

//                cc();


                                }
                            }, 5000);


                            System.out.println("123"+pathM.get(key)+"delete status" );

                        }
                    }
//                    mSelectedItemsIds.clear();

//                    afterOnclick();
//                    ca.notifyDataSetChanged();
                    Log.d(TAG,"delete clicked");
                    mode.finish(); // Action picked, so close the CAB
                    return true;

                case R.id.share:
                    shareMusic();
                    mode.finish(); // Action picked, so close the CAB
                    return true;



                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // remove selection
            afterOnclick();
            Log.d("TAG","action mode finishedc");
            removeSelection();
            mActionMode = null;
        }
    }


    // Method to share any image.
    private void shareMusic() {
        ArrayList<Uri> uris = new ArrayList<Uri>();
//        Intent share = new Intent(Intent.ACTION_SEND);
        Intent share =new Intent();
        share.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("audio/*");

        // Make sure you put example png image named myImage.png in your
        // directory
//        String imagePath = Environment.getExternalStorageDirectory()
//                + "/myImage.png";

        SparseBooleanArray selected = getSelectedIds();

        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                int key = selected.keyAt(i);
//                System.out.println("123" + key + "  " + selected.valueAt(i));
                String imagePath =  pathM.get(key);
                File imageFileToShare = new File(imagePath);
                Uri uri = Uri.fromFile(imageFileToShare);
                System.out.println("123"+uri);
                uris.add(uri);
//                share.putExtra(Intent.EXTRA_STREAM, uri);


            }
        }
        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);



        startActivity(Intent.createChooser(share, "Share Music!"));







    }


    private void scanaddedFile(String path) {
        try {

            Log.d(TAG,"deleting the file with location"+ path);
            MediaScannerConnection.scanFile(getActivity(), new String[] { path },
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            getActivity().getContentResolver()
                                    .delete(uri, null, null);
                        }
                    });

            Log.d("11111","successfully deleted the file with location"+ path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }












}
