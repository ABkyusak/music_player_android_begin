package com.example.kabhishek1.musicv1;

/**
 * Created by kumar abhi on 5/13/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapterGridView extends BaseAdapter {

    //    String  result;
    ArrayList<String> result;

    Context context;

    public static  int pos;

    static String playListIdentifier;

    ArrayList<String> imageId;
    ArrayList<String> playlistId;
    private static LayoutInflater inflater = null;



    public CustomAdapterGridView(GridViewActivity mainActivity, ArrayList<String> PlayListNameList, ArrayList<String> PlaylistImages, ArrayList<String> PlayListId) {
        // TODO Auto-generated constructor stub
        this.result = PlayListNameList;
        this.context = mainActivity;
        this.imageId = PlaylistImages;
        this.playlistId = PlayListId;
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
      if (position >= imageId.size() || imageId.get(position) == null || imageId.get(position) == " "  ) {
            holder.os_img.setImageResource(R.drawable.back2);
        } else

           holder.os_img.setImageURI(Uri.parse(imageId.get(position)));


        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_SHORT).show();
//           getPlayList( context, "253");

//                Intent i= new Intent(context,ListViewPlayList.class);
//                i.putExtra("pos",position);

//                ListViewPlayList plclass = new ListViewPlayList();

               pos = position;


                String playList =  playlistId.get(position);
                Intent i;
                i = new Intent(context,ListViewPlayList.class);
                i.putExtra("playListid",playList);
                context.startActivity(i);
//                plclass.kbc(context);



//               context.startActivity(i);
//                i = new Intent(CustomAdapterGridView.class,ListViewPlayList.class);

//                startActivity(i);
//                   ListViewPlayList lvp = new ListViewPlayList();

            }
        });

        return rowView;
    }




}
