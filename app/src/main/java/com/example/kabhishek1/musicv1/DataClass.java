package com.example.kabhishek1.musicv1;

import java.util.ArrayList;

/**
 * Created by k.abhishek1 on 18-05-2017.
 */

public class DataClass {

    ArrayList<String> prgmNameList;
    ArrayList<String> SongArtistName;
    ArrayList<String> ComposerName;
    ArrayList<String> pathM ;
    ArrayList<String> AsliArtist;
    ArrayList<String> Album;

    private static DataClass sInstance;
    public static  DataClass getInstance(ArrayList<String> prgmNameList, ArrayList<String> pathM ,ArrayList<String> SongArtistName, ArrayList<String> ComposerName, ArrayList<String> AsliArtist, ArrayList<String> Album)
    {
        if (sInstance == null) {
            sInstance = new DataClass(prgmNameList,pathM,SongArtistName, ComposerName,AsliArtist,  Album);
        }
        return sInstance;
    }

    public DataClass(ArrayList<String> prgmNameList, ArrayList<String> pathM , ArrayList<String> SongArtistName, ArrayList<String> ComposerName, ArrayList<String> AsliArtist, ArrayList<String> Album){
        this.prgmNameList = prgmNameList;
        this.pathM = pathM;
        this.SongArtistName = SongArtistName;
        this.ComposerName=ComposerName;
        this.AsliArtist = AsliArtist;
        this.Album = Album;
    }





}
