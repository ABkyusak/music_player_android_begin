package com.example.kabhishek1.musicv1.jmusixmatch.subtitle.get;

import com.example.kabhishek1.musicv1.jmusixmatch.subtitle.Subtitle;
import com.google.gson.annotations.SerializedName;

public class SubtitleGetBody {

    @SerializedName("subtitle")
    private Subtitle subtitle;

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }
}
