package com.example.kabhishek1.musicv1.jmusixmatch.snippet.get;

import com.google.gson.annotations.SerializedName;


public class SnippetGetMessage {

    @SerializedName("message")
    private SnippetGetContainer container;

    public void setContainer(SnippetGetContainer container) {
        this.container = container;
    }

    public SnippetGetContainer getContainer() {
        return container;
    }

}
