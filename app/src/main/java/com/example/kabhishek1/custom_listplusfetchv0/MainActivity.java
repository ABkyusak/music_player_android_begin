package com.example.kabhishek1.custom_listplusfetchv0;

import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,ActivityCompat.OnRequestPermissionsResultCallback {

    ListView lv;
    CustomAdapter ca;
    Context context;
    public static final String TAG = "123";
    ArrayList<String> pathM ;
    private ActionMode mActionMode;
    public SparseBooleanArray mSelectedItemsIds;
    ArrayList<String> prgmNameList;

    List<AudioModel> tempAudioList;


    HashMap<String,ArrayList<String>> pathMusic ;
    HashMap<String,String> pathAlbum ;

//    public String[] prgmImag;

    ArrayList<String> prgmImag;

    //    ArrayList prgmName;
    public static int[] prgmImages = {R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images, R.drawable.images};

//    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
   public MainActivity(){


   }

    private static final int REQUEST_READ_PERMISSION = 786;

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
    protected void onCreate(Bundle savedInstanceState) {


        Log.d(TAG, "MAin Activity Started");
        pathM = new ArrayList<>();
        prgmNameList = new ArrayList<>();
        prgmImag =  new ArrayList<>();



//        ArrayList<AudioModel> model = new ArrayList<>();
        super.onCreate(savedInstanceState);

        pathAlbum = new HashMap< >();

        setContentView(R.layout.activity_main);

        context = this;
        lv = (ListView) findViewById(R.id.listView);
        mSelectedItemsIds = new SparseBooleanArray();


        requestPermission();

        getPlayList(context);
        afterOnclick();




//        lv.setOnItemLongClickListener(this);


        // lv.setOnItemClickListener(this);


     /*   @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }*/
//        startService(new Intent(getBaseContext(), BackgroundSoundService.class));

    }

   /* protected void onResume() {
        super.onResume();


    }*/


   public void afterOnclick()
   {


       pathM.clear();
       prgmNameList.clear();
   mSelectedItemsIds.clear();
//    removeSelection();
       Log.d(TAG,"inside afterOnClick");

       List<AudioModel> list;
       getAllAudioFromDevice(this);
       getAllAlbumFromDevice(this);
       setAlbumIdAndPics();

       list =tempAudioList;

       System.out.println("123-----=====--------"+tempAudioList.get(0).getAlbumArt());



       for (int i = 0; i < 6; i++) {
           prgmNameList.add(list.get(i).getaName());
           pathM.add(list.get(i).getaPath());
           prgmImag.add(list.get(i).getAlbumArt());
//            model.add(list)
       }
//                 Log.d(TAG,"here");

        /*for(int i=0; i < prgmNameList.size();i++)
            Log.d(TAG,prgmNameList.get(i));*/

       lv.setOnItemLongClickListener(this);

       ca = new CustomAdapter(this, prgmNameList, prgmImag, pathM,mSelectedItemsIds);
       lv.setAdapter(ca);






       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if (mActionMode == null) {
                   Intent i = new Intent(context, MainActivity2.class);
                   Log.d(TAG, "inside the onitemClick");

//                Log.d(TAG,pathM.get(position)+"after intent");

                   int pos = position;
                   String str = String.valueOf(pos);
//                Log.d(TAG,"clicked on this "+ str);

                   i.putExtra("position", str);

                   i.putExtra("key", pathM);
//                i.putExtra("model", (Parcelable) model);

                   i.putExtra("name", prgmNameList);


                   i.putExtra("PATH", pathM.get(position));

//                Log.d(TAG,pathM.get(position)+"after intent");
                   context.startActivity(i);
//                Log.d(TAG,pathM.get(position)+"after intent");

               }else{
                   onListItemSelect(position,view);
                   view.setBackgroundColor(0x9934B5E4);
               }

           }
       });



   }


    public void getAllAudioFromDevice(final Context context) {



        tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.Media.ALBUM_ID};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                AudioModel audioModel = new AudioModel();
                String path = c.getString(0);
                String name = c.getString(1);
                String album = c.getString(2);
                String artist = c.getString(3);
//                String albumArt = c.getString(4);

                audioModel.setaName(name);
                audioModel.setaAlbum(album);

//                audioModel.setaAlbumId(artist);
                audioModel.setaAlbumId(artist);

                audioModel.setaPath(path);
//                audioModel.setAlbumArt(albumArt);

                Log.d(TAG, " Name :" + name);
                Log.d(TAG, " album-id :" + artist);

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

    public void setAlbumIdAndPics(){

        Log.d(TAG,"sfmsdfdofodkoskfdofkdofkdokokfosdkfksdfkdofkdfokdofk");

//        for  (int i =0 ;i < list.size(); i++){
//
//             String alb_id = list.get(i).getaAlbumId();
//            Log.d("TAG",alb_id +"----->   "+ pathAlbum.get(alb_id));
//            list.get(i).setAlbumArt(pathAlbum.get(alb_id));
//        }
//        Log.d("TAG",tempAudioList.size());
        System.out.println("123"+tempAudioList.size());
        for (AudioModel temp : tempAudioList) {

            String alb_id = temp.getaAlbumId();
            System.out.println("123 "+temp.getaAlbumId()+" "+pathAlbum.get(alb_id));

//            Log.d("TAG","1111111111111" + alb_id);
            if(pathAlbum.get(alb_id) != null) {
                temp.setAlbumArt(pathAlbum.get(alb_id));
                System.out.println("123 "+ "entered");
            }
            else {
                temp.setAlbumArt(" ");
            }

//            Log.d("TAG",temp.getAlbumArt()+"---------->"+temp.getaPath());

        }








//        for(int i =0; i < tempAudioList.size();i++){
////            Log.d("TAG",tempAudioList.get(i).getaPath());
//             Log.d("TAG",tempAudioList.get(i).getAlbumArt());
//
//        }



    }


    public  void getAllAlbumFromDevice(final Context context) {
//         List<AudioModel> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

//                AudioModel audioModel = new AudioModel();
                String path = c.getString(0);
                String name = c.getString(1);
//                String album = c.getString(2);
//                String artist = c.getString(3);
//                String albumArt = c.getString(4);

//                audioModel.setaName(name);
//                audioModel.setaAlbum(album);
//                audioModel.setaArtist(artist);
//                audioModel.setaPath(path);
//                audioModel.setAlbumArt(albumArt);


                Log.d(TAG, " album-id--------------------------------------------------> :" + path);
                Log.d(TAG, " album-art--------------------------------------------------> :" + name);

                pathAlbum.put(path,name);

//                tempAudioList.add(audioModel);
            }
            c.close();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);

        Log.d(TAG, "inside the inflate");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                //add the function to perform here
                return (true);
            case R.id.share:
                //add the function to perform here
                return (true);
            case R.id.aTP:
                //add the function to perform here
                return (true);
            case R.id.about:
                //add the function to perform here
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }




    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//         View view1 = null;// should be declared as global


    afterOnclick();
        Log.d(TAG,"inside the LongItem itemClick Listener ");
        onListItemSelect(position,view);
//        view.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
//                        : Color.TRANSPARENT);

//        view.setBackgroundColor(Color.parseColor("#222222"));
//        afterOnclick();






  // true is returned as i want no further processing of the for the onClickListener
        return true;
    }
    private void onListItemSelect(int position,View view) {

        toggleSelection(position,view);


        boolean hasCheckedItems = getSelectedCount() > 0;

        if (hasCheckedItems && mActionMode == null) {
            // there are some selected items, start the actionMode
            mActionMode = startActionMode(new ActionModeCallback());
            Log.d(TAG,"there are some selected items, start the actionMode");
//            mActionMode.setHomeButtonEnabled(true);
//            mActionMode.setDisplayHomeAsUpEnabled(true);
        } else if (!hasCheckedItems && mActionMode != null) {
            // there no selected items, finish the actionMode
            mActionMode.finish();
            afterOnclick();
            Log.d(TAG,"there no selected items, finish the actionMode");
        }

        if (mActionMode != null) {
            mActionMode.setTitle(String.valueOf(getSelectedCount()) + " selected");
//            view.setBackgroundColor(Color.parseColor("#222222"));
//            view.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4: Color.TRANSPARENT);

            Log.d(TAG,"selected item is "+position);
        }


//        ca = new CustomAdapter(this, prgmNameList, prgmImages, pathM,mSelectedItemsIds);
//        lv.setAdapter(ca);



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

//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                    builder1.setMessage("Do you want to delete the selected files ?");
//                    builder1.setCancelable(true);

                    List<String> mAnimals = new ArrayList<String>();
                    mAnimals.add("Cat");
                    mAnimals.add("Dog");
                    mAnimals.add("Horse");
                    mAnimals.add("Elephant");
                    mAnimals.add("Rat");
                    mAnimals.add("Lion");

                    final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setTitle("Animals");
                    dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String selectedText = Animals[item].toString();  //Selected item in listview

                            Log.d(TAG,"clicked in the listview in the alaerbox");
                        }
                    });
                    //Create alert dialog object via builder
                    AlertDialog alertDialogObject = dialogBuilder.create();
                    //Show the dialog
                    alertDialogObject.show();

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
//
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
            MediaScannerConnection.scanFile(context, new String[] { path },
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            context.getContentResolver()
                                    .delete(uri, null, null);
                        }
                    });

            Log.d("11111","successfully deleted the file with location"+ path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public ArrayList<String> getPlayList(final Context context) {

        ArrayList<String> arrayList=new ArrayList<String>();




        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Playlists._ID,MediaStore.Audio.Playlists.NAME};



        Cursor playListCursor= context.getContentResolver().query(uri, projection, null, null, null);


        String id="0";
        String name;
        if (playListCursor != null) {
            while (playListCursor.moveToNext()) {


                id = playListCursor.getString(0);
                name = playListCursor.getString(1);
                arrayList.add(name);


                System.out.println("playlist  "+id);
                System.out.println("playlist  "+name);


            }

          int playListid = Integer.parseInt(id);
            if(playListid > 0){
                String[] pro = {
                        MediaStore.Audio.Playlists.Members.AUDIO_ID,
                        MediaStore.Audio.Playlists.Members.ARTIST,
                        MediaStore.Audio.Playlists.Members.TITLE,
                        MediaStore.Audio.Playlists.Members._ID



                };


                playListCursor = null;



                playListCursor = this.managedQuery(
                        MediaStore.Audio.Playlists.Members.getContentUri("external",playListid ),
                        pro,
                        MediaStore.Audio.Media.IS_MUSIC +" != 0 ",
                        null,
                        null);



                if (playListCursor != null) {
                    while (playListCursor.moveToNext()) {


                        id = playListCursor.getString(0);
                        name = playListCursor.getString(1);
                        String title = playListCursor.getString(2);
//                        arrayList.add(name);


                        System.out.println("playlist  "+"song_id  " + id);
                        System.out.println("playlist  "+"song_artist_name " + name);
                        System.out.println("playlist  "+"song_title " + title);


                    }
                }








                }







            playListCursor.close();
        }

       return  arrayList;

    }







}
