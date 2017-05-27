package com.example.kabhishek1.musicv1;

/**
 * Created by k.abhishek1 on 17-04-2017.
 */


/**
 * Created by k.abhishek1 on 12-04-2017.
 */

public class AudioModel {
    String aPath;
    String aName;
    String aAlbum;
    String aArtist;
    String albumArt;
     String Albumid;
    String asliArtist;
    private String composer;





    public String getaPath() {
        return aPath;
    }

    public void setaPath(String aPath) {
        this.aPath = aPath;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaAlbum() {
        return aAlbum;
    }

    public void setaAlbum(String aAlbum) {
        this.aAlbum = aAlbum;
    }




    public void setaAlbumId( String Albumid) {
      this.Albumid = Albumid;

    }

    public String getaAlbumId() {

        return Albumid;
    }



    public String getaArtist() {
        return aArtist;
    }

    public void setaArtist(String aArtist) {
        this.aArtist = aArtist;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getAlbumArt() {
//        this.albumArt = albumArt;

        return albumArt;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }
    public String getComposer() {
        return composer;
    }

    public void setAsliArtist(String asliArtist) {
        this.asliArtist = asliArtist;
    }
    public String getAsliArtist() {
        return asliArtist;
    }
}