package com.example.kabhishek1.custom_listplusfetchv0;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> pathM;
    ArrayList<String> imageId;
    private ActionMode mActionMode;
    private SparseBooleanArray mSelectedItemsIds;

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
    public CustomAdapter(MainActivity mainActivity, ArrayList<String> prgmNameList, ArrayList<String> prgmImages, ArrayList<String> pathM, SparseBooleanArray mSelectedItemsIds) {
        // TODO Auto-generated constructor stub

        Log.d(TAG,"new custom adapter constructor called");
        this.result=prgmNameList;
        this.context=mainActivity;
        this.imageId=prgmImages;
        this.pathM = pathM;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemChecked = new boolean[6];

        for(int i =0; i <6; i++)
            itemChecked[i] = true;

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

        Log.d(TAG,imageId.get(position));



        // if there is no image fetched from the media Store then set this image as default imag
        if(imageId.get(position) == " ") {
            holder.img.setImageURI( Uri.parse("/storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1492506753915"));
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
                            popup.getMenuInflater().inflate(R.menu.activity_main_actions,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.aTP:

                                            //Or Some other code you want to put here.. This is just an example.
//                                             Toast.makeText(context, " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                            List<String> mAnimals = new ArrayList<String>();
                                            mAnimals.add("Cat");
                                            mAnimals.add("Dog");
                                            mAnimals.add("Horse");
                                            mAnimals.add("Elephant");
                                            mAnimals.add("Rat");
                                            mAnimals.add("Lion");


                                            // display the current playlist that i have created
                                            final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                            dialogBuilder.setTitle("Add To PlatList");
                                            dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int item) {
                                                    String selectedText = Animals[item].toString();  //Selected item in listview

                                                    Log.d(TAG,"clicked in the listview in the alartbox");



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
                                                                    public void onClick(DialogInterface dialog,int id) {
                                                                        // get user input and set it to result
                                                                        // edit text
            //                                                            result.setText(userInput.getText());

                                                                        System.out.println("cccc  "+userInput.getText());
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

}