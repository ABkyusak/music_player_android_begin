package com.example.kabhishek1.musicv1;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> pathM;
    ArrayList<String> imageId;
    private ActionMode mActionMode;
    private SparseBooleanArray mSelectedItemsIds;
     ArrayList<String> worldpopulationlist ;
    private ArrayList<String> arraylist;

    Context context;
    MediaPlayer mediaPlayer;
//    int [] imageId;
    int [] checkId;
    boolean[] itemChecked;
    Holder holder;
//    MainActivity main;
//    ArrayList<int>pos;

    public static final String TAG = "987";
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, ArrayList<String> prgmNameList, ArrayList<String> prgmImages, ArrayList<String> pathM, SparseBooleanArray mSelectedItemsIds) {
        // TODO Auto-generated constructor stub

        Log.d(TAG,"new custom adapter constructor called");
//        this.worldpopulationlist = prgmNameList;
        this.result=prgmNameList;
        this.context=context;
        this.imageId=prgmImages;
        this.pathM = pathM;

        this.worldpopulationlist = new ArrayList<String >();
        this.worldpopulationlist.addAll(result);

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        itemChecked = new boolean[6];
//
//        for(int i =0; i <6; i++)
//            itemChecked[i] = true;

//     mSelectedItemsIds = new SparseBooleanArray();
       this.mSelectedItemsIds = mSelectedItemsIds;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
//
//    public void toggleSelection(int position) {
//        selectView(position, !mSelectedItemsIds.get(position));
//    }

//    public void selectView(int position, boolean value) {
//        if (value)
//            mSelectedItemsIds.put(position, value);
//        else
//            mSelectedItemsIds.delete(position);
//
//        notifyDataSetChanged();
//    }
//
//    static int getSelectedCount() {
//        return mSelectedItemsIds.size();
//    }
//    public  void removeSelection() {
//        mSelectedItemsIds = new SparseBooleanArray();
//        notifyDataSetChanged();
//    }

    public class Holder
    {
        TextView tv;
        ImageView img;
        ImageView imageview;
//        CheckBox chk;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.program_list, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.imageview = (ImageView) rowView.findViewById(R.id.menu_i);

        holder.tv.setText(result.get(position));

//        Log.d("ImageView",imageId.get(position));



//         if there is no image fetched from the media Store then set this image as default imag
        if(imageId.get(position) == " " || imageId.get(position) == null) {
                 holder.img.setImageResource(R.drawable.back1);
        }else

            holder.img.setImageURI( Uri.parse(imageId.get(position)));



        Log.d(TAG,"inside the set background");
        System.out.println("987     "+mSelectedItemsIds.get(position));

        //on selecting the long press change the colour

    rowView.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4: Color.TRANSPARENT);


        try {

            // add to playlist pop up menu will appear

            holder.imageview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    switch (v.getId()) {
                        case R.id.menu_i:

                            PopupMenu popup = new PopupMenu(context, v);
                            popup.getMenuInflater().inflate(R.menu.menu_playlist,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.aTP:

                                            //Or Some other code you want to put here.. This is just an example.
//                                             Toast.makeText(context, " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                            ArrayList<String> PlayList = new ArrayList<String>() ;
//                                            mAnimals.add("Cat");
//                                            mAnimals.add("Dog");
//                                            mAnimals.add("Horse");
//                                            mAnimals.add("Elephant");
//                                            mAnimals.add("Rat");
//                                            mAnimals.add("Lion");


                                            // display the current playlist that i have created

                                            musicTab1 m = new musicTab1();
                                            PlayList = m.getAllPlayList(context);

                                            final CharSequence[] Animals = PlayList.toArray(new String[PlayList.size()]);

                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                                            dialogBuilder.setTitle("Add To PlatList");

                                            dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int item) {

                                                    String selectedText = Animals[item].toString();

//                                                    String s = "try";
                                                    Log.d(TAG,"selected playList is"+selectedText);

                                                   final int playL_id = idForplaylist(context,selectedText);
                                                    System.out.println("987"+"playlist id for selected playlist is---------------->"+ playL_id);
//                                                                               String playL_id =  fetchPlaylistid(context,"hfufufu");
                                                   final String song_id;
                                                    String song_n = result.get(position);
                                                    System.out.println("987"+"song to be added is"+song_n);
//                                                    Log.d(TAG,song_n);
//                                                    System.out.println("987"+"---------------->"+ playL_id);
                                                    song_id = fetchSongid(context,song_n);
                                                    System.out.println("987"+ "song id of the song to be added is"+song_id);


                                                    final Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
//                                                 Do something after 5s = 5000ms
                                                            addTrackToPlaylist(context,String.valueOf(song_id),playL_id, 1);


                                                        }
                                                    }, 1000);



                                                    //Selected item in listview


                                                     Log.d(TAG,"song "+song_id+"successfully-added"+ "in playlist-id"+playL_id);
                                                    Log.d(TAG,"clicked in the listview in the alartbox  "+ selectedText);



                                                }
                                            });






                                            dialogBuilder.setPositiveButton(
                            "Create a new PlayList",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

//                                     EditText res = (EditText) findViewById(R.id.editTextResult);


                                                //create a new playlist name
                                                      LayoutInflater li = LayoutInflater.from(context);
                                                      View promptsView = li.inflate(R.layout.prompts, null);

                                                     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                             context);

                                                // set prompts.xml to alertdialog builder
                                                alertDialogBuilder.setView(promptsView);


                                                final EditText userInput = (EditText) promptsView
                                                        .findViewById(R.id.editTextDialogUserInput);

                                                // set dialog message
                                                alertDialogBuilder
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, final int id) {
                                                                        // get user input and set it to result
                                                                        // edit text
            //                                                            result.setText(userInput.getText());


                                                                        //write your code here for playlist creation in content provider
                                                                      final String s = String.valueOf(userInput.getText());


                                                                         addnewPlaylist(context,s);



                                                                        final Handler handler = new Handler();
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
//                                                 Do something after 5s = 5000ms
                                                                                int playL_id = idForplaylist(context,s);
                                                                                System.out.println("987"+"playlist id for selected playlist is---------------->"+ playL_id);
                                                                                String song_n = result.get(position);
                                                                                System.out.println("987"+"song to be added is"+song_n);
//                                                    Log.d(TAG,song_n);
//                                                    System.out.println("987"+"---------------->"+ playL_id);
                                                                                String song_id = fetchSongid(context,song_n);

                                                                                addTrackToPlaylist(context,String.valueOf(song_id),playL_id, 1);

                                                                            }
                                                                        }, 1000);


















                                                                    }
                                                                })
                                                              .setNegativeButton("Cancel",
                                                                       new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog,int id) {
                                                                               dialog.cancel();
                                                                       }
                                                                  });

                                                // create alert dialog
                                                     AlertDialog alertDialog = alertDialogBuilder.create();

                                                // show it
                                                      alertDialog.show();









                                                  dialog.cancel();
                                }
                            });

                                            dialogBuilder.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
//
                                            //Create alert dialog object via builder



                                            AlertDialog alertDialogObject = dialogBuilder.create();
                                            //Show the dialog
                                            alertDialogObject.show();

                                            break;

                                        case R.id.search:

                                            Toast.makeText(context, "Add to Wish List Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();

                                            break;

                                        default:
                                            break;
                                    }

                                    return true;
                                }
                            });

                            break;

                        default:
                            break;
                    }


                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }





        Log.d(TAG,"again");


        return rowView;
    }



    public void addnewPlaylist(Context context, String newplaylist) {

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Audio.Playlists.NAME, newplaylist);
        resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values);

    }



    private int idForplaylist(Context context,String name) {
        Cursor c;
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        c = context.getContentResolver().query(uri, new String[] { MediaStore.Audio.Playlists._ID },
                MediaStore.Audio.Playlists.NAME + "=?",
                new String[] {name},
                null);
        int id = -1;
        if (c != null) {

            System.out.println("playlist"+ " done");
            c.moveToFirst();
            if (!c.isAfterLast()) {
                id = c.getInt(0);

            }
            c.close();
        }
        return id;
    }

    public String fetchSongid(Context context,String name){



        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;;

        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.TITLE};

        String id = " 0";
        String path;

        Cursor play = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.TITLE+ " =?", new String[] {name} , null);
//       Cursor play= context.getContentResolver().query(uri, projection,null, null,MediaStore.Audio.Playlists._ID+" DESC");

        if (play != null) {
            while (play.moveToNext()) {

//                System.out.println("playlist"+"no "+ i);


                id = play.getString(0);
                path = play.getString(1);
                name = play.getString(2);



                System.out.println("987"+" -id  " + "jugaaad "+id);
                System.out.println("987"+" path  " + "jugaaad "+path);
                System.out.println("987"+" name  " + "jugaaad "+name);

                break;


            }
        }
        return id;


    }

    public String fetchPlaylistid(Context context,String name) {

        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME};

        String id = " 0";

   Cursor play = context.getContentResolver().query(uri, projection, MediaStore.Audio.Playlists._ID+ " =?", new String[] {name} , null);
//       Cursor play= context.getContentResolver().query(uri, projection,null, null,MediaStore.Audio.Playlists._ID+" DESC");

        if (play != null) {
            while (play.moveToNext()) {

//                System.out.println("playlist"+"no "+ i);


                 id = play.getString(0);
                 name = play.getString(1);


                System.out.println("playlist  " + "jugaaad "+id);
                System.out.println("playlist  " + "jugaaad "+name);

                break;


            }
        }
        return id;
    }


    public void addTrackToPlaylist(Context context, String audio_id,
                                   long playlist_id, int pos) {
        Uri newuri = MediaStore.Audio.Playlists.Members.getContentUri(
                "external", playlist_id);
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, pos);
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audio_id);
        values.put(MediaStore.Audio.Playlists.Members.PLAYLIST_ID,
                playlist_id);
        resolver.insert(newuri, values);

    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        System.out.println("987"+"inside the filter function"+charText);
//        worldpopulationlist = result;
        System.out.println("987"+"inside the filter function"+charText+ "--->  "+ worldpopulationlist.size());
        result.clear();
        if (charText.length() == 0) {
            result.addAll(worldpopulationlist);
            Log.d(TAG," o length");
        }
        else
        {
//            System.out.println("987"+"inside the filter function"+charText+ "--->  "+ worldpopulationlist.size());

            for (int i =0; i < worldpopulationlist.size(); i++)
            {
                if (worldpopulationlist.get(i).toLowerCase(Locale.getDefault()).contains(charText))
                {
                    result.add(worldpopulationlist.get(i));
//                    System.out.println("987"+"inside the filter function"+charText+ "--->  "+ worldpopulationlist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }



}