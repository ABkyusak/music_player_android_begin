package com.example.kumarabhi.customlistviewv0;

/**
 * Created by kumar abhi on 4/17/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.start;

public class CustomAdapter extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> pathM;
    Context context;
    MediaPlayer mediaPlayer;
    int [] imageId;
    public static final String TAG = "987";
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, ArrayList<String> prgmNameList, int[] prgmImages,ArrayList<String> pathM) {
        // TODO Auto-generated constructor stub
        this.result=prgmNameList;
        this.context=mainActivity;
        this.imageId=prgmImages;
        this.pathM = pathM;
        inflater = ( LayoutInflater )context.
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

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result.get(position));
        holder.img.setImageResource(imageId[position]);


        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Log.d(TAG,pathM.get(position)+"after intent");
//              Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();








           Intent i = new Intent(context,MainActivity2.class);
             Log.d(TAG,pathM.get(position)+"after intent");
//              i.putStringArrayListExtra("PATH", ));
              i.putExtra("PATH",pathM.get(position));


               Log.d(TAG,pathM.get(position)+"after intent");
             context.startActivity(i);
                Log.d(TAG,pathM.get(position)+"after intent");
            }
        });
        return rowView;
    }

}