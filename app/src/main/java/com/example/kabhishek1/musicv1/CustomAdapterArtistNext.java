

/**
 * Created by kumar abhi on 5/24/2017.
 */
package com.example.kabhishek1.musicv1;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterArtistNext extends RecyclerView.Adapter<CustomAdapterArtistNext.MyViewHolder> {

    ArrayList<String> songPath;
    ArrayList<String> songName;
    ArrayList<String> moviesList;
    ArrayList<String> songAlbumPath;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView songN;
        public ImageView songImg;

        public MyViewHolder(View view) {
            super(view);
            songImg = (ImageView) view.findViewById(R.id.imageView1);
            songN = (TextView) view.findViewById(R.id.textView1);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public CustomAdapterArtistNext(ArrayList<String> songPath, ArrayList<String> songName, ArrayList<String> songAlbumPath) {
        this.songPath = songPath;
         this.songName = songName;
         this.songAlbumPath = songAlbumPath;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customadapterartistnext, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        String movie = moviesList.get(position);
        holder.songN.setText(songName.get(position));

        if(songAlbumPath.get(position) == " " || songAlbumPath.get(position) == null)
            holder.songImg.setImageResource(R.drawable.back1);
        else

            holder.songImg.setImageURI(Uri.parse(songAlbumPath.get(position)));

//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());




    }

    @Override
    public int getItemCount() {
        return songPath.size();
    }
}
