package com.example.kabhishek1.musicv1.jmusixmatch.snippet.get;

import com.example.kabhishek1.musicv1.jmusixmatch.snippet.Snippet;
import com.google.gson.annotations.SerializedName;

public class SnippetGetBody {

    @SerializedName("snippet")
    private Snippet snippet;

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
