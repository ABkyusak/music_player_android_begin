package com.example.kabhishek1.musicv1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumar abhi on 5/15/2017.
 */


public class CustomAdapterGridViewListView extends BaseAdapter {
    ArrayList<String> result;
    ArrayList<String> pathM;
    ArrayList<String> imageId;


    Context context;


    Holder holder;


    public static final String TAG = "987";
    private static LayoutInflater inflater = null;

    public CustomAdapterGridViewListView(Context context, ArrayList<String> prgmNameList, ArrayList<String> prgmImages, ArrayList<String> pathM) {
        // TODO Auto-generated constructor stub

        Log.d(TAG, "new custom adapter constructor called");
        this.result = prgmNameList;
        this.context = context;
        this.imageId = prgmImages;
        this.pathM = pathM;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        itemChecked = new boolean[6];

//        for(int i =0; i <6; i++)
//            itemChecked[i] = true;

//     mSelectedItemsIds = new SparseBooleanArray();
//        this.mSelectedItemsIds = mSelectedItemsIds;

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
    public boolean isEnabled(int position) {
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

    public class Holder {
        TextView tv;
        ImageView img;
//        ImageView imageview;

//        CheckBox chk;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        holder = new Holder();
        View rowView;

        System.out.println("listview"+result.get(position)+ imageId.get(position));

        rowView = inflater.inflate(R.layout.program_list, null);

        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
//        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
//        holder.imageview = (ImageView) rowView.findViewById(R.id.menu_i);


        holder.tv.setText(result.get(position));

       Log.d("ImageView",imageId.get(position));


//         if there is no image fetched from the media Store then set this image as default imag
      if (imageId.get(position) == " " || imageId.get(position) == null) {
            holder.img.setImageResource(R.drawable.back1);
        } else

            holder.img.setImageURI(Uri.parse(imageId.get(position)));


        //on selecting the long press change the colour
        return rowView;

    }
}