package com.example.kabhishek1.musicv1;

/**
 * Created by kumar abhi on 5/13/2017.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterGridViewForDisplay extends BaseAdapter {

    //    String  result;
    ArrayList<String> result;

    Context context;

    static  int pos;

    static String playListIdentifier;

    ArrayList<String> imageId;
    ArrayList<String> playlistId;
    private static LayoutInflater inflater = null;



    public CustomAdapterGridViewForDisplay(Context context, ArrayList<String> PlayListNameList, ArrayList<String> PlaylistImages) {
        // TODO Auto-generated constructor stub
        this.result = PlayListNameList;
        this.context = context;
        this.imageId = PlaylistImages;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

    public class Holder {
        TextView os_text;
        ImageView os_img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.os_text = (TextView) rowView.findViewById(R.id.os_texts);
        holder.os_img = (ImageView) rowView.findViewById(R.id.os_images);
//        holder.os_img.setLayoutParams(new GridView.LayoutParams(70, 70));

        holder.os_text.setText(result.get(position));
//
        if (position >= imageId.size() || imageId.get(position) == " " || imageId.get(position) == null ) {
           holder.os_img.setImageResource(R.drawable.back1);
        } else

            holder.os_img.setImageURI(Uri.parse(imageId.get(position)));




        return rowView;
    }




}
