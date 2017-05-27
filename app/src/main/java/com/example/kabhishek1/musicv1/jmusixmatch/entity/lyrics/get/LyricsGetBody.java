package com.example.kabhishek1.musicv1.jmusixmatch.entity.lyrics.get;

import com.example.kabhishek1.musicv1.jmusixmatch.entity.lyrics.Lyrics;
import com.google.gson.annotations.SerializedName;

public class LyricsGetBody {
	
    @SerializedName("lyrics")
    private Lyrics lyrics;

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }

    public Lyrics getLyrics() {
        return lyrics;
    }
}
